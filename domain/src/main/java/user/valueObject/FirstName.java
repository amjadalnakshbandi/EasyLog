package user.valueObject;
import constants.constants;
import java.io.IOException;
import java.util.Objects;

public class FirstName {
    private final String firstName;

    public FirstName(String firstName) throws IOException {
        validateFirstName(firstName);
        this.firstName = firstName;
    }

    private void validateFirstName(String firstName) throws IOException {
        if (firstName == null || firstName.isBlank()) {
            throw new IOException("First name cannot be null or empty");
        }
        if (!firstName.matches(constants.NAME_RULE)) {
            throw new IOException("First name must only contain letters, apostrophes, or hyphens, and be 1–50 characters long");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FirstName firstName1 = (FirstName) o;
        return Objects.equals(firstName, firstName1.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(firstName);
    }
}
