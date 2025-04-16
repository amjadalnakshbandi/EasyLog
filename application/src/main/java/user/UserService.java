package user;

import user.aggregate.Employees;
import user.entity.User;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class UserService {
    public void addUserImplementation(User user) {
        String csvFile = "Data/users.csv";
       try {
            // Check if file exists and has content
            boolean fileExists = Files.exists(Paths.get(csvFile)) && Files.size(Paths.get(csvFile)) > 0;

            FileWriter writer = new FileWriter(csvFile, true); // append mode

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
            e.printStackTrace();
        }
    }

    public List<Employees> getAllEmployeeImplementation() {
        return List.of();
    }
}
