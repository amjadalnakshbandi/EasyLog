package user.valueObject;

import java.util.Objects;
import java.util.Random;

public class UserID {
    private static final Random random = new Random();
    private final long id;

    // Random ID constructor (used for registration)
    public UserID() {
        this.id = 100000L + random.nextInt(900000);
    }

    // For loading from CSV or external systems
    public UserID(long id) {
        this.id = id;
    }

    // Convenience for String-based sources
    public UserID(String idStr) {
        this.id = Long.parseLong(idStr);
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserID userID = (UserID) o;
        return id == userID.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
