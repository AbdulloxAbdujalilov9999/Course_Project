package com.epam.app.service;

import com.epam.app.dto.ProductDTO;
import com.epam.app.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    void productsHome();
    void productsMenu();
    void searchProduct();
    List<ProductDTO> findAllByParam(int index, String value);
    List<ProductDTO> getAllByParam(int index, String paramName);
    Optional<ProductDTO> getOneByParam(int index, String value, List<String> products);
    List<ProductDTO> getAllSortedProducts(String property, String ascOrDesc, List<Product> products);
    void allProductsBySorting();
    List<Product> findAllProducts();
}
