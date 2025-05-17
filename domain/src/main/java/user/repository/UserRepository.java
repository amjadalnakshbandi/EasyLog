package user.repository;

import user.aggregate.Employees;
import user.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserRepository {
    void addUser(User user) throws IOException;
    void loginUser(User user) throws IOException;
    void logoutUser(User user) throws IOException;
    List<Employees> getAllEmployee() throws IOException;
}
