package order.valueObject;
import java.io.IOException;
import java.util.Objects;

public class BranchName {

    private final String name;

    public BranchName(String name) throws IOException {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) throws IOException
    {
        if (name == null || name.isBlank()) {
            throw new IOException("Branch name cannot be null or empty");
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BranchName that = (BranchName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }


}
