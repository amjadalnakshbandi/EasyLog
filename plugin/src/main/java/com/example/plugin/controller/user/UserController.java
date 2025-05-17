package com.example.plugin.controller.user;

import com.example.plugin.persistence.user.UserRepositoryBridge;
import com.example.plugin.model.user.LoginResponse;
import com.example.plugin.model.user.AddUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.ApiResponse;
import response.ErrorResponse;
import response.SuccessResponse;
import user.UserDto;
import user.entity.User;
import user.valueObject.*;

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

    // 🧾 Add User (Registration)
    @PostMapping
    public ResponseEntity<? extends ApiResponse> addUser(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody UserDto dto) {
        try {
            // Validate Authorization header
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("Fehlender oder ungültiger Authorization-Header", "Unauthorized"));
            }

            String tokenValue = authHeader.substring(7); // Strip "Bearer "

            if (!isValidAdminToken(tokenValue)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ErrorResponse("Token ist nicht berechtigt, Benutzer zu erstellen", "Forbidden"));
            }

            Role role = parseRole(dto.getRole());

            User user = new User(
                    new UserID(UUID.randomUUID().toString()),
                    new FirstName(dto.getFirstName()),
                    new LastName(dto.getLastName()),
                    new Password(dto.getPassword()),
                    new Email(dto.getEmail()),
                    role,
                    new Token()
            );

            userRepositoryBridge.addUser(user);

            // Construct response DTO
            UserDto responseDto = new UserDto(
                    user.getUserID().toString(),
                    user.getFirstName().getFirstName(),
                    user.getLastName().getLastname(),
                    user.getEmail().getEmail(),
                    user.getRole().toString()
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponse<>("Benutzerkonto erfolgreich erstellt", responseDto));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(e.getMessage(), "Internal Server Error"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage(), "Validation Error"));
        }
    }

    // 🔐 Login User
    @PostMapping("/login")
    public ResponseEntity<? extends ApiResponse> loginUser(@RequestBody UserDto loginDto) {
        try {
            User user = new User(
                    new Email(loginDto.getEmail()),
                    new Password(loginDto.getPassword()),
                    new Token()
            );

            userRepositoryBridge.loginUser(user); // populates token etc.

            // ✅ Build response with just email + token
            LoginResponse response = new LoginResponse(
                    user.getEmail().getEmail(),
                    user.getToken().getToken()
            );

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Login erfolgreich", response));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(e.getMessage(), "Authentication Failed"));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(e.getMessage(), "Internal Server Error"));
        }
    }




    // 🚪 Logout User
    @PostMapping("/logout")
    public ResponseEntity<? extends ApiResponse> logoutUser(@RequestBody UserDto logoutDto) {
        try {
            if (logoutDto.getEmail() == null || logoutDto.getToken() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse("Email und Token sind erforderlich", "Validation Error"));
            }

            User user = new User(
                    new Email(logoutDto.getEmail()),
                    new Token(logoutDto.getToken())
            );

            userRepositoryBridge.logoutUser(user);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Logout erfolgreich", null));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(e.getMessage(), "Internal Server Error"));
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

    private boolean isValidAdminToken(String token) {
        return "ASE".equals(token); // Super admin token
    }
}
