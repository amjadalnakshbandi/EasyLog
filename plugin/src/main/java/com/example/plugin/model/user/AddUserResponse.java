package com.example.plugin.model.user;
import user.UserDto;

public class AddUserResponse {
    private final UserDto user;

    public AddUserResponse(UserDto user) {
        this.user = user;
    }

    public UserDto getUser() {
        return user;
    }
}
