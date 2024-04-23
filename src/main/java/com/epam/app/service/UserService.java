package com.epam.app.service;

import com.epam.app.entity.User;

import java.util.List;

public interface UserService {
    void loginMenu();
    void login();
    void register();
    void registerUser(User user);
    List<String> findAllUsers();
}
