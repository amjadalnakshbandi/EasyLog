package com.example.plugin.persistence.user;

import user.UserService;
import user.aggregate.Employees;
import user.entity.User;
import user.repository.UserRepository;

import java.io.IOException;
import java.util.List;


public class UserRepositoryBridge implements UserRepository {
    @Override
    public void addUser(User user) throws IOException {
        UserService userService = new UserService();
        userService.addUserImplementation(user);
    }

    @Override
    public List<Employees> getAllEmployee() throws IOException {
        UserService userService = new UserService();
        return userService.getAllEmployeeImplementation() ;
    }
}
