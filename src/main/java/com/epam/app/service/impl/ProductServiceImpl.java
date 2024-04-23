package com.epam.app.service.impl;

import com.epam.app.dto.ProductDTO;
import com.epam.app.entity.Product;
import com.epam.app.service.FileReaderService;
import com.epam.app.service.ProductService;
import com.epam.app.service.SortingService;
import com.epam.app.service.mapper.ProductMapper;
import com.epam.app.service.rowmapper.ProductRowMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static com.epam.app.SearchSystem.runSystem;
import static com.epam.app.constant.AnsiColorConstants.*;
import static com.epam.app.constant.Constants.BED_LINES_FILE_PATH;
import static com.epam.app.util.SystemUtils.searchSystemHeader;
import static com.epam.app.util.SystemUtils.wrongEntered;

public class ProductServiceImpl implements ProductService {
    private final FileReaderService fileReaderService;
    private final SortingService sortingService;
    private final ProductMapper productMapper;
    private static final Scanner in = new Scanner(System.in);

    public ProductServiceImpl(FileReaderService fileReaderService, SortingService sortingService, ProductMapper productMapper) {
        this.fileReaderService = fileReaderService;
        this.sortingService = sortingService;
        this.productMapper = productMapper;
    }

    @Override
    public void productsHome() {
        searchSystemHeader();
        productsMenu();
    }

    @Override
    public void productsMenu() {
        System.out.print("Press [1] - Search product, [2] - Search one [3] - All products [0] - Log out\n>>> ");
        String loginOrRegister = in.next();

        switch (loginOrRegister) {
            case "1" -> searchProduct();
            case "2" -> searchOneProduct();
            case "3" -> allProductsBySorting();
            case "0" -> runSystem();
            default -> {
                wrongEntered(); productsMenu();
            }
        }
    }

    @Override
    public void searchProduct() {
        System.out.println("What parameter do you want to enter for the search?");
        System.out.print("Press [1] - ID, [2] - NAME, [3] - CATEGORY, [4] - PRICE, [0] - BACK\n>>> ");
        List<ProductDTO> productsDTO = new ArrayList<>();

        String choose = in.next();
        switch (choose) {
            case "1" -> productsDTO = getAllByParam(0, "ID");
            case "2" -> productsDTO = getAllByParam(1, "NAME");
            case "3" -> productsDTO = getAllByParam(2, "CATEGORY");
            case "4" -> productsDTO = getAllByParam(3, "PRICE");
            case "0" -> productsHome();
            default -> {
                wrongEntered(); searchProduct();
            }
        }

        productsDTO.forEach(System.out::println);
        productsMenu();
    }
    @Override
    public List<ProductDTO> getAllByParam(int index, String paramName) {
        Scanner inputParam = new Scanner(System.in);
        System.out.printf("Enter the %s to search: ", paramName);
        String value = inputParam.nextLine();

        List<ProductDTO> products = findAllByParam(index, value);
        if (products == null || products.isEmpty()) {
            System.out.println(ANSI_YELLOW + "Product not found with value: " + value + ANSI_RESET);
            productsMenu();
            return null;
        }
        System.out.println(ANSI_GREEN + "All products with " + paramName + ": " + value + ANSI_RESET);
        return products;
    }

    private void searchOneProduct() {
        System.out.println("What parameter do you want to enter for the search?");
        System.out.print("Press [1] - ID, [2] - NAME, [3] - CATEGORY, [4] - PRICE, [0] - BACK\n>>> ");

        String choose = in.next();
        switch (choose) {
            case "1" -> searchOneProduct(0, "ID");
            case "2" -> searchOneProduct(1, "NAME");
            case "3" -> searchOneProduct(2, "CATEGORY");
            case "4" -> searchOneProduct(3, "PRICE");
            case "0" -> productsHome();
            default -> {
                wrongEntered(); searchProduct();
            }
        }

        productsMenu();
    }

    private void searchOneProduct(int index, String paramName) {
        var stringList = fileReaderService.readAll(BED_LINES_FILE_PATH);
        if (stringList == null || stringList.isEmpty()) {
            System.out.println(ANSI_YELLOW + "Product not found!" + ANSI_RESET);
            productsMenu();
            return;
        }

        Scanner inputParam = new Scanner(System.in);
        System.out.printf("Enter the %s to search: ", paramName);
        String value = inputParam.nextLine();

        Optional<ProductDTO> optionalProductDTO = getOneByParam(index, value, stringList);
        if (optionalProductDTO.isPresent()) {
            System.out.println(ANSI_GREEN + "Result get one product: " + ANSI_RESET);
            System.out.println(optionalProductDTO.get());
        }
    }

    @Override
    public Optional<ProductDTO> getOneByParam(int index, String value, List<String> stringList) {
        ProductRowMapper rowMapper = new ProductRowMapper();
        var optionalProduct = stringList.stream()
                .map(product -> product.split(":"))
                .filter(product -> product[index].equalsIgnoreCase(value))
                .map(rowMapper::mapRow)
                .findFirst();

        if (optionalProduct.isEmpty()) {
            System.out.println(ANSI_YELLOW + "Product not found by param: " + value + ANSI_RESET);
            return Optional.empty();
        }

        return optionalProduct
                .map(productMapper::toDTO);
    }

    @Override
    public void allProductsBySorting() {
        System.out.println("Select param to sort products:");
        System.out.print("Press [1] - ID, [2] - NAME, [3] - CATEGORY, [4] - PRICE, [0] - BACK\n>>> ");
        while (true) {
            String choose = in.next();
            switch (choose) {
                case "1" -> selectSortedOrderAndGetResult("ID");
                case "2" -> selectSortedOrderAndGetResult("NAME");
                case "3" -> selectSortedOrderAndGetResult("CATEGORY");
                case "4" -> selectSortedOrderAndGetResult("PRICE");
                case "0" -> productsHome();
                default -> {
                    wrongEntered(); allProductsBySorting();
                }
            }
        }
    }

    @Override
    public List<ProductDTO> findAllByParam(int index, String value) {
        List<String> stringList = fileReaderService.readAll(BED_LINES_FILE_PATH);
        if (stringList.isEmpty()) {
            return null;
        }

        ProductRowMapper rowMapper = new ProductRowMapper();
        var products = stringList.stream()
                .map(product -> product.split(":"))
                .filter(product -> product[index].equalsIgnoreCase(value))
                .map(rowMapper::mapRow)
                .toList();

        return productMapper.toDTOList(products);
    }


    @Override
    public List<Product> findAllProducts() {
        List<String> stringList = fileReaderService.readAll(BED_LINES_FILE_PATH);
        if (stringList.isEmpty()) {
            return null;
        }

        ProductRowMapper rowMapper = new ProductRowMapper();
        return stringList.stream()
                .map(product -> product.split(":"))
                .map(rowMapper::mapRow)
                .toList();
    }


    private void selectSortedOrderAndGetResult(String propertyName) {
        System.out.println("Select in what order to sort?");
        System.out.print("Press [1] - ASC, [2] - DESC, [0] - BACK\n>>> ");
        var products = findAllProducts();
        List<ProductDTO> sortedProducts = new ArrayList<>();
        while (true) {
            String choose = in.next();
            switch (choose) {
                case "1" -> sortedProducts = getAllSortedProducts(propertyName, "ASC", products);
                case "2" -> sortedProducts = getAllSortedProducts(propertyName, "DESC", products);
                case "0" -> allProductsBySorting();
                default -> {
                    wrongEntered(); searchProduct();
                }
            }
            break;
        }

        System.out.println(ANSI_GREEN + "All products:" + ANSI_RESET);
        sortedProducts.forEach(System.out::println);
        productsMenu();
    }

    @Override
    public List<ProductDTO> getAllSortedProducts(String property, String ascOrDesc, List<Product> products) {
        var entityList = sortingService.resolveSort(property, ascOrDesc, products);
        return productMapper.toDTOList(entityList);
    }

}
