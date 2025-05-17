package user.entity;

import user.valueObject.*;

public class User {

    private final UserID userID;
    private final FirstName firstName;
    private final LastName lastName;
    private final Password password;
    private final Token token;
    private final Email email;
    private final Role role;

    // ✅ Full constructor for registration
    public User(UserID userID,
                FirstName firstName,
                LastName lastName,
                Password password,
                Email email,
                Role role,
                Token token) {

        if (userID == null || firstName == null || lastName == null ||
                password == null || email == null || role == null || token == null) {
            throw new IllegalArgumentException("All fields must be non-null");
        }

        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.role = role;
        this.token = token;
    }

    // ✅ Minimal constructor for login
    public User(Email email, Password password, Token token) {
        if (email == null || password == null || token == null) {
            throw new IllegalArgumentException("Email, password, and token are required for login");
        }

        this.email = email;
        this.password = password;
        this.token = token;

        this.userID = null;
        this.firstName = null;
        this.lastName = null;
        this.role = null;
    }

    // ✅ Constructor for logout
    public User(Email email, Token token) {
        if (email == null || token == null) {
            throw new IllegalArgumentException("Email and token are required for logout");
        }

        this.email = email;
        this.token = token;

        this.userID = null;
        this.firstName = null;
        this.lastName = null;
        this.password = null;
        this.role = null;
    }


    // Constructor for loading users from CSV
    public User(UserID userID,
                FirstName firstName,
                LastName lastName,
                Password password,
                Email email,
                Role role) {

        if (userID == null || firstName == null || lastName == null ||
                password == null || email == null || role == null) {
            throw new IllegalArgumentException("All fields must be non-null");
        }

        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.role = role;
        this.token = null; // Token not required in this context
    }

    // Getters
    public UserID getUserID() {
        return userID;
    }

    public FirstName getFirstName() {
        return firstName;
    }

    public LastName getLastName() {
        return lastName;
    }

    public Password getPassword() {
        return password;
    }

    public Email getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", password=" + password +
                ", token=" + token +
                ", email=" + email +
                ", role=" + role +
                '}';
    }
}
