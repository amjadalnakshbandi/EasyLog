package com.example.plugin.controller.user;

import com.example.plugin.persistence.user.UserRepositoryBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.ErrorResponse;
import response.SuccessResponse;
import user.UserDto;
import user.entity.User;
import user.valueObject.*;
import response.ApiResponse;
import response.ErrorResponse;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepositoryBridge userRepositoryBridge;

    @Autowired
    public UserController(UserRepositoryBridge userRepositoryBridge) {
        this.userRepositoryBridge = userRepositoryBridge;
    }

    @PostMapping
    public ResponseEntity<? extends ApiResponse> addUser(@RequestBody UserDto dto) {
        try {
            // Convert role from String to enum
            Role role = parseRole(dto.getRole());

            // Create domain value objects
            UserID userID = new UserID(UUID.randomUUID().toString());
            FirstName firstName = new FirstName(dto.getFirstName());
            LastName lastName = new LastName(dto.getLastName());
            Email email = new Email(dto.getEmail());
            Password password = new Password(dto.getPassword());

            // Create the User entity
            User user = new User.Builder()
                    .userID(userID)
                    .firstName(firstName)
                    .lastName(lastName)
                    .password(password)
                    .email(email)
                    .role(role)
                    .build();

            // Persist the user
            userRepositoryBridge.addUser(user);

            // Create response using the actual values from value objects
            UserResponse response = new UserResponse(
                    user.getUserID().getId(),
                    new UserDto(
                            user.getFirstName().getFirstName(),
                            user.getLastName().getLastname(),
                            user.getEmail().getEmail(),
                            user.getPassword().getPassword(),
                            user.getRole().toString()
                    )
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponse<>(
                            "Benutzerkonto erfolgreich erstellt",
                            response
                    ));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(
                            e.getMessage(),
                            "Validation Error"
                    ));
        }
    }

    private Role parseRole(String role) {
        if (role == null) throw new IllegalArgumentException("Role is required");
        return switch (role.trim().toLowerCase()) {
            case "admin" -> Role.ADMIN;
            case "employee", "mitarbeiter" -> Role.MITARBEITER;
            default -> throw new IllegalArgumentException("Invalid role: " + role);
        };
    }

    // Simple response wrapper class
    private static class UserResponse {
        private final String userId;
        private final UserDto userDetails;

        public UserResponse(long userId, UserDto userDetails) {
            this.userId = String.valueOf(userId);
            this.userDetails = userDetails;
        }

        // Getters
        public String getUserId() { return userId; }
        public UserDto getUserDetails() { return userDetails; }
    }
}