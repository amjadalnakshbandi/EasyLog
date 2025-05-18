package user;

import constants.constants;
import user.aggregate.Employees;
import user.entity.User;
import user.valueObject.Role;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    Path csv_users_path = Paths.get(constants.CSV_USER_PATH);
    Path csv_login_path = Paths.get(constants.CSV_Login_PATH);


    public void addUserImplementation(User user) throws IOException {
        try {
            boolean fileExists = Files.exists(csv_users_path) && Files.size(csv_users_path) > 0;
            try (FileWriter writer = new FileWriter(String.valueOf(csv_users_path), true)) {
                if (!fileExists) {
                    writer.append("UserID,firstName,lastName,email,password,role\n");
                }
                writer.append(String.valueOf(user.getUserID().getId())).append(",");
                writer.append(user.getFirstName().getFirstName()).append(",");
                writer.append(user.getLastName().getLastname()).append(",");
                writer.append(user.getEmail().getEmail()).append(",");
                writer.append(user.getPassword().getPassword()).append(",");
                writer.append(user.getRole().toString()).append("\n");
            }
        } catch (IOException e) {
            throw new IOException("Error saving user to CSV", e);
        }
    }

    public void loginUserImplementation(User user) throws IOException {
        String userId = null;
        String firstName = null;
        String lastName = null;
        String role = null;
        boolean credentialsMatch = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(csv_users_path)))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length >= 6) {
                    String storedEmail = userData[3].trim();
                    String storedPassword = userData[4].trim();

                    if (storedEmail.equals(user.getEmail().getEmail()) &&
                            storedPassword.equals(user.getPassword().getPassword())) {
                        userId = userData[0].trim();
                        firstName = userData[1].trim();
                        lastName = userData[2].trim();
                        role = userData[5].trim();
                        credentialsMatch = true;
                        break;
                    }
                }
            }
        }

        if (!credentialsMatch) {
            throw new IllegalArgumentException("Login failed: Invalid email or password");
        }

        if (user.getToken() == null || user.getToken().getToken() == null) {
            throw new IllegalArgumentException("Token is missing");
        }

        List<String> updatedLines = new ArrayList<>();
        boolean loginExists = false;
        boolean loginFileExists = Files.exists(csv_login_path);

        if (loginFileExists) {
            try (BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(csv_login_path)))) {
                String header = reader.readLine();
                updatedLines.add(header);

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] loginData = line.split(",");
                    if (loginData.length >= 5) {
                        String existingEmail = loginData[3].trim();
                        if (existingEmail.equals(user.getEmail().getEmail())) {
                            String newLine = String.join(",", userId, firstName, lastName,
                                    user.getEmail().getEmail(), role,
                                    user.getToken().getToken());
                            updatedLines.add(newLine);
                            loginExists = true;
                        } else {
                            updatedLines.add(line);
                        }
                    }
                }
            }
        }

        if (!loginExists) {
            if (!loginFileExists) {
                updatedLines.add("userId,firstName,lastName,email,role,token");
            }
            String newEntry = String.join(",", userId, firstName, lastName,
                    user.getEmail().getEmail(), role,
                    user.getToken().getToken());
            updatedLines.add(newEntry);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(csv_login_path), false))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        }
    }

    public void logoutUserImplementation(User user) {
        try {
            File loginFile = new File(String.valueOf(csv_login_path));
            if (!loginFile.exists()) {
                throw new IOException("Login file does not exist.");
            }

            List<String> updatedLines = getUpdateLines(user);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(csv_login_path), false))) {
                for (String updatedLine : updatedLines) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error during logout", e);
        }
    }

    public List<Employees> getAllEmployeeImplementation(String token) {
        if (!constants.SUPER_ADMIN_TOKEN.equals(token)) {
            throw new SecurityException("Access denied: Only SuperAdmin can view all users.");
        }

        List<Employees> employeesList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(csv_users_path)))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 6) continue;

                String roleStr = data[5].trim().toUpperCase();

                Role role;
                try {
                    role = Role.valueOf(roleStr);
                } catch (IllegalArgumentException e) {
                    continue; // Skip unknown roles
                }

                if (role != Role.ADMIN && role != Role.MITARBEITER) continue;

                Employees employee = getEmployees(data, role);
                employeesList.add(employee);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error reading users file", e);
        }

        return employeesList;
    }



    private List<String> getUpdateLines(User user) throws IOException {
        List<String> updatedLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(csv_login_path)))) {
            String header = reader.readLine();
            if (header != null) {
                updatedLines.add(header);
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] loginData = line.split(",");
                if (loginData.length >= 4) {
                    String existingEmail = loginData[3].trim();
                    if (!existingEmail.equals(user.getEmail().getEmail())) {
                        updatedLines.add(line);
                    }
                }
            }
        }
        return updatedLines;
    }

    private static Employees getEmployees(String[] data, Role role) throws IOException {
        User user = new User(
                new user.valueObject.UserID(data[0].trim()),
                new user.valueObject.FirstName(data[1].trim()),
                new user.valueObject.LastName(data[2].trim()),
                new user.valueObject.Password(data[4].trim()),
                new user.valueObject.Email(data[3].trim()),
                role
        );

        Employees employee = new Employees();
        employee.setUser(user);
        return employee;
    }

    public boolean isValidAdminToken(String token) throws IOException {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token must not be null or empty");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(csv_login_path)))) {
            String line;
            reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length >= 6) {
                    String tokenFromCsv = columns[5].trim();
                    String role = columns[4].trim().toLowerCase();

                    if (tokenFromCsv.equals(token) && role.equals("admin")) {
                        return true;
                    }
                }
            }

            return false; // token not found or role isn't admin
        } catch (IOException e) {
            throw new IOException("Error by read the login CSV", e);
        }
    }

    public Role parseRole(String role) {
        if (role == null) throw new IllegalArgumentException("Role is required");
        return switch (role.trim().toLowerCase()) {
            case "admin" -> Role.ADMIN;
            case "employee", "mitarbeiter" -> Role.MITARBEITER;
            default -> throw new IllegalArgumentException("Invalid role: " + role);
        };
    }
}
