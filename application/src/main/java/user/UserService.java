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

    public void addUserImplementation(User user) {

        try {
            // Check if file exists and has content
            boolean fileExists = Files.exists(csv_users_path) && Files.size(csv_users_path) > 0;

            FileWriter writer = new FileWriter(String.valueOf(csv_users_path), true); // append mode

            if (!fileExists) {
                // Write header
                writer.append("UserID,firstName,lastName,email,password,role\n");
            }

            // Write user data
            writer.append(String.valueOf(user.getUserID().getId())).append(",");
            writer.append(user.getFirstName().getFirstName()).append(",");
            writer.append(user.getLastName().getLastname()).append(",");
            writer.append(user.getEmail().getEmail()).append(",");
            writer.append(user.getPassword().getPassword()).append(",");
            writer.append(user.getRole().toString()).append("\n");

            writer.close();

            //System.out.println("User added to CSV successfully: " + user.toString());
        } catch (IOException e) {
            System.err.println("Error saving user to CSV: " + e.getMessage());
        }
    }
    public void loginUserImplementation(User user) throws IOException {
        // System.out.println("📥 Received login request for: " + user.getEmail().getEmail());

        String userId = null;
        String firstName = null;
        String lastName = null;
        String role = null;
        boolean credentialsMatch = false;

        // Step 1: Verify user credentials from users.csv
        try (BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(csv_users_path)))) {
            String line;
            reader.readLine(); // skip header

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
            System.err.println("⚠️ Token is null");
        }

        // Step 2: Read existing login entries from logins.csv
        List<String> updatedLines = new ArrayList<>();
        boolean loginExists = false;
        boolean loginFileExists = Files.exists(csv_login_path);

        if (loginFileExists) {
            try (BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(csv_login_path)))) {
                String header = reader.readLine();
                updatedLines.add(header); // retain header

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] loginData = line.split(",");
                    if (loginData.length >= 5) {
                        String existingEmail = loginData[3].trim();

                        if (existingEmail.equals(user.getEmail().getEmail())) {
                            // Replace with updated token
                            String newLine = String.join(",", userId, firstName, lastName,
                                    user.getEmail().getEmail(), role,
                                    user.getToken() != null ? user.getToken().getToken() : "null");
                            updatedLines.add(newLine);
                            loginExists = true;
                        } else {
                            updatedLines.add(line); // keep other lines as-is
                        }
                    }
                }
            }
        }

        // Step 3: If login didn't exist, append new entry
        if (!loginExists) {
            if (!loginFileExists) {
                updatedLines.add("userId,firstName,lastName,email,role,token");
            }

            String newEntry = String.join(",", userId, firstName, lastName,
                    user.getEmail().getEmail(), role,
                    user.getToken() != null ? user.getToken().getToken() : "null");
            updatedLines.add(newEntry);
        }

        // Step 4: Write back to logins.csv
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
                System.err.println("⚠️ Login file does not exist.");
                return;
            }

            List<String> updatedLines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(csv_login_path)))) {
                String header = reader.readLine();
                if (header != null) {
                    updatedLines.add(header); // Keep the header
                }

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] loginData = line.split(",");
                    if (loginData.length >= 4) {
                        String existingEmail = loginData[3].trim();
                        if (!existingEmail.equals(user.getEmail().getEmail())) {
                            updatedLines.add(line); // Keep other users
                        }
                    }
                }
            }

            // Write the updated lines back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(csv_login_path), false))) {
                for (String updatedLine : updatedLines) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
            }

            System.out.println("✅ User logged out successfully: " + user.getEmail().getEmail());

        } catch (IOException e) {
            System.err.println("❌ Error during logout: " + e.getMessage());
        }
    }
    public List<Employees> getAllEmployeeImplementation() {
        List<Employees> employeesList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(csv_users_path)))) {
            reader.readLine(); // Skip header

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 6) continue; // Skip invalid lines

                String roleStr = data[5].trim().toUpperCase();

                Role role;
                try {
                    role = Role.valueOf(roleStr);
                } catch (IllegalArgumentException e) {
                    System.err.println("⚠️ Unknown role: " + roleStr);
                    continue;
                }

                if (role != Role.ADMIN && role != Role.MITARBEITER) continue;

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
                employeesList.add(employee);
            }

        } catch (IOException e) {
            System.err.println("❌ Error reading users file: " + e.getMessage());
        }

        return employeesList;
    }


}