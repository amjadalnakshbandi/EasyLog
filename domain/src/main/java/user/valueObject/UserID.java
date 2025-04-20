package user.valueObject;

import java.util.Objects;
import java.util.Random;

public class UserID {
    private static final Random random = new Random();
    private final long id;

    public UserID(String string) {
        // Generates a random number between 100000 and 999999 (6 digits)
        this.id = 100000L + random.nextInt(900000);
    }

    @Override
    public String toString() {
        return ""+id;
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
