package order.valueObject;
import java.io.IOException;
import java.util.Objects;

public class BranchID {
    private final String branchID;

    public BranchID(String branchID) throws IOException {
        validateBranchID(branchID);
        this.branchID = branchID;
    }

    public String getBranchID() {
        return branchID;
    }

    private void validateBranchID(String branchID) throws IOException
    {
        if ( branchID == null || branchID.isBlank()) {
            throw new IOException("Branch ID cannot be null or empty");
        }
        if (!branchID.matches("^B\\d{3}$")) {
            throw new IOException("Branch ID must start with 'B' followed by exactly 3 digits (e.g., B123)");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BranchID branchID1 = (BranchID) o;
        return Objects.equals(branchID, branchID1.branchID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(branchID);
    }


}
