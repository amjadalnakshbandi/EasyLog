package com.example.plugin.persistence.user;

import user.UserService;
import user.aggregate.Employees;
import user.entity.User;
import user.repository.UserRepository;
import user.valueObject.Token;

import java.io.IOException;
import java.util.List;


public class UserRepositoryBridge implements UserRepository {
    @Override
    public void addUser(User user) throws IOException {
        UserService userService = new UserService();
        userService.addUserImplementation(user);
    }

    @Override
    public void loginUser(User user) throws IOException {
        UserService userService = new UserService();
        userService.loginUserImplementation(user);
    }

    @Override
    public void logoutUser(User user) throws IOException {
        UserService userService = new UserService();
        userService.logoutUserImplementation(user);

    }

    @Override
    public List<Employees> getAllEmployee(Token token) throws IOException {
        UserService userService = new UserService();
        return userService.getAllEmployeeImplementation(token.getToken());
    }



}
