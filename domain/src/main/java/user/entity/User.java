package user.entity;

import user.valueObject.*;

public class User {

    private final UserID userID;
    private final FirstName firstName;
    private final LastName lastName;
    private final Password password;
    private final Email email;
    private final Role role;

    private User(Builder builder) {
        this.userID = builder.userID;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.password = builder.password;
        this.email = builder.email;
        this.role = builder.role;
    }

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

    public static class Builder {
        private UserID userID;
        private FirstName firstName;
        private LastName lastName;
        private Password password;
        private  Email email;
        private Role role;

        public Builder() {
            // Default constructor
        }

        public Builder userID(UserID userID) {
            this.userID = userID;
            return this;
        }

        public Builder firstName(FirstName firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(LastName lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder password(Password password) {
            this.password = password;
            return this;
        }

        public Builder email(Email email) {
            this.email = email;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public String toString() {
        return "UserID: " + userID.getId() + "\n" +
                "First Name: " + firstName.getFirstName() + "\n" +
                "Last Name: " + lastName.getLastname() + "\n" +
                "Password: " + password.getPassword() + "\n" +
                "Email: " + email.getEmail() + "\n" +
                "Role: " + role;
    }
}
