package com.epam.app.controller;

import com.epam.app.service.UserService;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    public void loginMenu() {
        userService.loginMenu();
    }
}
