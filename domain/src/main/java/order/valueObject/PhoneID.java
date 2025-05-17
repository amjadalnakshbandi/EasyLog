package order.valueObject;

import java.io.IOException;
import java.util.Objects;

public class PhoneID {
    private final String phoneID;

    public PhoneID(String phoneID) throws IOException {
        validatePhoneID(phoneID);
        this.phoneID = phoneID;
    }

    public String getPhoneID() {
        return phoneID;
    }

    private void validatePhoneID(String phoneID) throws IOException {
        if (phoneID == null || phoneID.isBlank()) {
            throw new IOException("Phone ID cannot be null or empty");
        }
        if (!phoneID.matches("^P\\d{3}$")) {
            throw new IOException("Phone ID must start with 'P' followed by exactly 3 digits (e.g., P123)");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PhoneID phoneID1 = (PhoneID) o;
        return Objects.equals(phoneID, phoneID1.phoneID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(phoneID);
    }
}
