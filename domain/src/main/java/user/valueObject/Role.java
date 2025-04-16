package user.valueObject;

public enum Role {
    ADMIN,
    MITARBEITER;

    @Override
    public String toString() {
        // Convert the enum value to lowercase
        return this.name().toLowerCase();
    }
}
