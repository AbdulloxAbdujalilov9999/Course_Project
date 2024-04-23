package com.epam.app.controller;

import com.epam.app.service.ProductService;

public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public void productsHome() {
        productService.productsHome();
    }
}
