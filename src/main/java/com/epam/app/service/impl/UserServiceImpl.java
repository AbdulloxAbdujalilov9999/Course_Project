package com.epam.app.service.impl;

import com.epam.app.entity.User;
import com.epam.app.service.FileReaderService;
import com.epam.app.service.UserService;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static com.epam.app.constant.AnsiColorConstants.*;
import static com.epam.app.constant.Constants.USER_FILE_PATH;
import static com.epam.app.util.SystemUtils.wrongEntered;

public class UserServiceImpl implements UserService {
    private final FileReaderService fileReaderService;
    private static final Scanner in = new Scanner(System.in);

    public UserServiceImpl(FileReaderService fileReaderService) {
        this.fileReaderService = fileReaderService;
    }

    @Override
    public void loginMenu() {
        System.out.print("Press [1] - To login, [2] - To register [0] - Stop System\n>>> ");
        String loginOrRegister = in.next();
        switch (loginOrRegister) {
            case "1" -> login();
            case "2" -> register();
            case "0" -> System.exit(0);
            default -> {
                wrongEntered(); loginMenu();
            }
        }
    }

    @Override
    public void login() {
        System.out.print("Enter your email: ");
        String email = in.next();
        System.out.print("Enter your password: ");
        String password = in.next();

        Boolean ifUserExists = IfUserExists(email, password);
        if (!ifUserExists) {
            System.out.println(
                    ANSI_YELLOW + "User not found by email or password. Please register first!" + ANSI_RESET
            );
            loginMenu();
        }
    }

    @Override
    public void register() {
        System.out.print("Enter your registration details: \nEmail: ");
        String email = in.next();
        Boolean ifUserExists = IfUserExists(email);
        if (ifUserExists) {
            System.out.println(ANSI_RED + "User with the same email already exists" + ANSI_RESET);
            register();
        }
        System.out.print("Enter your password: ");
        String password = in.next();
        if (email.contains(":") || password.contains(":")) {
            System.out.println(ANSI_RED +"Invalid character(':') entered. Please re-enter!" + ANSI_RESET);
            register();
        }

        registerUser(new User().email(email).password(password));
    }

    @Override
    public void registerUser(User user) {
        try {
            FileWriter fileWriter = new FileWriter(USER_FILE_PATH, true);
            fileWriter.write(user.fileFormat());

            fileWriter.close();
        } catch (IOException e) {
            System.out.println(ANSI_RED + "Exception while creating files. Caused by: " + e.getMessage() + ANSI_RESET);
            System.exit(-1);
        }

        System.out.println(ANSI_GREEN + "You have successfully registered!" + ANSI_RESET);
    }

    @Override
    public List<String> findAllUsers() {
        return fileReaderService.readAll(USER_FILE_PATH);
    }

    private Boolean IfUserExists(String email) {
        var users = findAllUsers();
        if (users.isEmpty()) return false;
        Optional<String> optionalEmail = users.stream()
                .map(userDetail -> userDetail.split(":")[0])
                .filter(findEmail -> findEmail.equals(email))
                .findAny();

        return optionalEmail.isPresent();
    }

    private Boolean IfUserExists(String email, String password) {
        var users = findAllUsers();
        if (users.isEmpty()) return false;
        Optional<String[]> optionalUser = users.stream()
                .map(userDetail -> userDetail.split(":"))
                .filter(userDetail -> userDetail[0].equals(email) && userDetail[1].equals(password))
                .findAny();

        return optionalUser.isPresent();
    }
}

