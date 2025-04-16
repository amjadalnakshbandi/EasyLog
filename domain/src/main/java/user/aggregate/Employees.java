package user.aggregate;

import user.entity.User;

import java.util.Objects;

public class Employees {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Employees employees = (Employees) o;
        return Objects.equals(user, employees.user);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(user);
    }
}
