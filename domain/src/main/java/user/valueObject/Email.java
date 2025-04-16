package user.valueObject;

import constants.constants;

import java.io.IOException;
import java.util.Objects;

public class Email {
    private final String email;

    public Email(String email) throws IOException {
        validateEmail(email);
        this.email = email;
    }

    private void validateEmail(String email) throws IOException {
        if (email == null || email.isBlank()) {
            throw new IOException("Email cannot be null or empty");
        }
        if (!email.matches(constants.EMAIL_RULE)) {
            throw new IOException("Invalid email format");
        }
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Email other = (Email) o;
        return Objects.equals(email, other.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }


}
