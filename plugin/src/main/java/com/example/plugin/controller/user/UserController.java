package com.example.plugin.controller.user;

import com.example.plugin.persistence.user.UserRepositoryBridge;
import com.example.plugin.model.user.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.ApiResponse;
import response.ErrorResponse;
import response.SuccessResponse;
import user.UserDto;
import user.aggregate.Employees;
import user.entity.User;
import user.valueObject.*;
import user.UserService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepositoryBridge userRepositoryBridge;
    private final UserService userService;

    @Autowired
    public UserController(UserRepositoryBridge userRepositoryBridge, UserService userService) {
        this.userRepositoryBridge = userRepositoryBridge;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<? extends ApiResponse> addUser(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody UserDto dto) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("Fehlender oder ungültiger Authorization-Header", "Unauthorized"));
            }

            String tokenValue = authHeader.substring(7);

            if (!userService.isValidAdminToken(tokenValue)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ErrorResponse("Token ist nicht berechtigt, Benutzer zu erstellen", "Forbidden"));
            }

            Role role = userService.parseRole(dto.getRole());

            User user = new User(
                    new UserID(),
                    new FirstName(dto.getFirstName()),
                    new LastName(dto.getLastName()),
                    new Password(dto.getPassword()),
                    new Email(dto.getEmail()),
                    role,
                    new Token()
            );

            userRepositoryBridge.addUser(user);

            UserDto responseDto = new UserDto(
                    user.getFirstName().getFirstName(),
                    user.getLastName().getLastname(),
                    user.getEmail().getEmail(),
                    user.getPassword().getPassword(),
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
        }catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage(), "Security Error"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<? extends ApiResponse> loginUser(@RequestBody UserDto loginDto) {
        try {
            User user = new User(
                    new Email(loginDto.getEmail()),
                    new Password(loginDto.getPassword()),
                    new Token()
            );

            userRepositoryBridge.loginUser(user);

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
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage(), "Security Error"));
        }
    }

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

        }  catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(e.getMessage(), "Authentication Failed"));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(e.getMessage(), "Internal Server Error"));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage(), "Security Error"));
        }
    }

    @GetMapping
    public ResponseEntity<? extends ApiResponse> getAllUsers(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("Fehlender oder ungültiger Authorization-Header", "Unauthorized"));
            }

            String token = authHeader.substring(7); // remove "Bearer "

            List<Employees> employees = userRepositoryBridge.getAllEmployee(new Token(token)); // Pass token

            List<UserDto> userDtos = employees.stream()
                    .map(emp -> {
                        User user = emp.getUser();
                        return new UserDto(
                                user.getFirstName().getFirstName(),
                                user.getLastName().getLastname(),
                                user.getEmail().getEmail(),
                                user.getPassword().getPassword(),
                                user.getRole().toString()
                        );
                    })
                    .toList();

            return ResponseEntity.ok(new SuccessResponse<>("Benutzer erfolgreich geladen", userDtos));

        }  catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(e.getMessage(), "Authentication Failed"));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(e.getMessage(), "Internal Server Error"));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage(), "Security Error"));
        }
    }

}
