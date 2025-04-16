package user.valueObject;

import constants.constants;

import java.io.IOException;
import java.util.Objects;

public class LastName {

    private final String lastname;

    public LastName(String lastname) throws IOException {
        validateLastName(lastname);
        this.lastname = lastname;
    }

    private void validateLastName(String lastname) throws IOException {
        if (lastname == null || lastname.isBlank()) {
            throw new IOException("Last name cannot be null or empty");
        }
        if (!lastname.matches(constants.NAME_RULE)) {
            throw new IOException("Last name must only contain letters, apostrophes, or hyphens, and be 1–50 characters long");
        }
    }

    public String getLastname() {
        return lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LastName lastName = (LastName) o;
        return Objects.equals(lastname, lastName.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(lastname);
    }
}
