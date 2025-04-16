package user.valueObject;

import constants.constants;

import java.io.IOException;
import java.util.Objects;

public class Password {
    private final String password;

    public Password(String password) throws IOException {
        validatePassword(password);
        this.password = password;
    }

    private void validatePassword(String password) throws IOException {
        if (password == null || password.isBlank()) {
            throw new IOException("Password cannot be null or empty");
        }
        if (!password.matches(constants.PASSWORD_RULE)) {
            throw new IOException("Password does not meet the required format");
        }
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Password other = (Password) o;
        return Objects.equals(password, other.password);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(password);
    }

}
