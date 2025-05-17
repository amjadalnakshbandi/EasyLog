package user;

public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private String token;

    // Default constructor for JSON deserialization
    public UserDto() {}

    // Constructor for login response
    public UserDto(String email, String token) {
        this.email = email;
        this.token = token;
    }

    // Constructor to add a user
    public UserDto(String firstName, String lastName, String email, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
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

}
