package com.epam.app.service.mapper;

import com.epam.app.dto.ProductDTO;
import com.epam.app.entity.Product;

import java.util.List;

public class ProductMapper {
    public ProductDTO toDTO(Product product) {
        var dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCategory(product.getCategory());
        dto.setPrice(product.getPrice());
        dto.setTotalAmount(product.getTotalAmount());

        return dto;
    }

    public Product toEntity(ProductDTO dto) {
        var entity = new Product();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCategory(dto.getCategory());
        entity.setPrice(dto.getPrice());
        entity.setTotalAmount(dto.getTotalAmount());

        return entity;
    }

    public List<ProductDTO> toDTOList(List<Product> products) {
        return products.stream()
                .map(this::toDTO)
                .toList();
    }
}
