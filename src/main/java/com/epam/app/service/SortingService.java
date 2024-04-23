package com.epam.app.service;

import com.epam.app.entity.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortingService {
    public List<Product> resolveSort(String propertyName, String ascOrDesc, List<Product> products) {
        List<Product> sortedProducts;
        switch (propertyName) {
            case "NAME" -> sortedProducts = sortByName(products, ascOrDesc);
            case "CATEGORY" -> sortedProducts = sortByCategory(products, ascOrDesc);
            case "PRICE" -> sortedProducts = sortByPrice(products, ascOrDesc);
            default -> sortedProducts = sortById(products, ascOrDesc);
        }

        return sortedProducts;
    }

    private List<Product> sortById(List<Product> products, String ascOrDesc) {
        var modifiableList = new ArrayList<>(products);

        Comparator<Product> comparator;
        if (ascOrDesc.equals("ASC"))
            comparator = Comparator.comparing(Product::getId);
        else comparator = (h1, h2) -> h2.getId().compareTo(h1.getId());
        modifiableList.sort(comparator);

        return modifiableList;
    }


    private List<Product> sortByName(List<Product> products, String ascOrDesc) {
        var modifiableList = new ArrayList<>(Collections.unmodifiableList(products));

        Comparator<Product> comparator;
        if (ascOrDesc.equals("ASC"))
            comparator = Comparator.comparing(Product::getName);
        else comparator = (h1, h2) -> h2.getName().compareTo(h1.getName());
        modifiableList.sort(comparator);

        return modifiableList;
    }

    private List<Product> sortByCategory(List<Product> products, String ascOrDesc) {
        var modifiableList = new ArrayList<>(Collections.unmodifiableList(products));

        Comparator<Product> comparator;
        if (ascOrDesc.equals("ASC"))
            comparator = Comparator.comparing(Product::getCategory);
        else comparator = (h1, h2) -> h2.getCategory().compareTo(h1.getCategory());
        modifiableList.sort(comparator);

        return modifiableList;
    }

    private List<Product> sortByPrice(List<Product> products, String ascOrDesc) {
        var modifiableList = new ArrayList<>(Collections.unmodifiableList(products));

        Comparator<Product> comparator;
        if (ascOrDesc.equals("ASC"))
            comparator = Comparator.comparing(Product::getPrice);
        else comparator = (h1, h2) -> h2.getPrice().compareTo(h1.getPrice());
        modifiableList.sort(comparator);

        return modifiableList;
    }
}
