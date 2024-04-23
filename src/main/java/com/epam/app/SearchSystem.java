package com.epam.app;

import com.epam.app.controller.ProductController;
import com.epam.app.controller.UserController;
import com.epam.app.service.FileReaderService;
import com.epam.app.service.ProductService;
import com.epam.app.service.SortingService;
import com.epam.app.service.UserService;
import com.epam.app.service.impl.FileReaderServiceImpl;
import com.epam.app.service.impl.ProductServiceImpl;
import com.epam.app.service.impl.UserServiceImpl;
import com.epam.app.service.mapper.ProductMapper;

public class SearchSystem {
    private static final FileReaderService fileReaderService = new FileReaderServiceImpl();
    private static final UserService userService = new UserServiceImpl(fileReaderService);
    private static final UserController userController = new UserController(userService);
    public static void main(String[] args) {
        runSystem();
    }

    public static void runSystem() {
        System.out.println("             Bed Lines and Dishes Werehouse ©2024");
        System.out.println("=============== Welcome to the search system ===============\n");
        userController.loginMenu();
        enterTheWareHouse();
    }

    public static void enterTheWareHouse() {
        SortingService sortingService = new SortingService();
        ProductMapper productMapper = new ProductMapper();
        ProductService productService = new ProductServiceImpl(fileReaderService, sortingService, productMapper);
        ProductController productController = new ProductController(productService);

        productController.productsHome();
    }
}
