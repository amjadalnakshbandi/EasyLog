package user;

public class UserDto {
    private String userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private String token;

    // Default constructor for JSON deserialization
    public UserDto() {}

    // Constructor to add a user
    public UserDto(String firstName, String lastName, String email, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Constructor for login response
    public UserDto(String email, String token) {
        this.email = email;
        this.token = token;
    }

    // Getters
    public String getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }

    // Setters
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
