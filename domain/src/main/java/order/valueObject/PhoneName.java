package order.valueObject;

import constants.constants;

import java.io.IOException;
import java.util.Objects;

public class PhoneName {

    private final String name;

    public PhoneName(String name) throws IOException {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) throws IOException {
        if (name == null || name.isBlank()) {
            throw new IOException("Phone name cannot be null or empty");
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PhoneName that = (PhoneName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}

