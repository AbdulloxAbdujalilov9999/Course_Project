package com.epam.app.service.rowmapper;

import com.epam.app.entity.Product;

public class ProductRowMapper {
    public Product mapRow(String [] row) {
        Product product = new Product();

        product.setId(Long.valueOf(row[0]));
        product.setName(row[1]);
        product.setCategory(row[2]);
        product.setPrice(Long.valueOf(row[3]));
        product.setTotalAmount(Long.valueOf(row[4]));

        return product;
    }
}
