package com.epam.app;

import com.epam.app.service.FileReaderService;
import com.epam.app.service.ProductService;
import com.epam.app.service.SortingService;
import com.epam.app.service.impl.FileReaderServiceImpl;
import com.epam.app.service.impl.ProductServiceImpl;
import com.epam.app.service.mapper.ProductMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static com.epam.app.TestConstants.*;
import static com.epam.app.constant.Constants.BED_LINES_FILE_PATH;
import static com.epam.app.constant.Constants.BED_LINES_FILE_PATH;
import static org.junit.jupiter.api.Assertions.*;

public class SearchSystemTest {
    private final FileReaderService fileReaderService = new FileReaderServiceImpl();
    private final SortingService sortingService = new SortingService();
    private final ProductMapper productMapper = new ProductMapper();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ProductService productService = new ProductServiceImpl(
            fileReaderService, sortingService, productMapper
    );

    @ParameterizedTest
    @MethodSource("getArguments")
    void testGetAllProductsByParam(int indexOfColumn, String value, int countElements) throws JsonProcessingException {
        var response = productService.findAllByParam(indexOfColumn, value);
        assertNotNull(response);
        assertEquals(countElements, response.size());
        if (indexOfColumn == 0) assertEquals(Long.valueOf(PRODUCT_ID), response.get(0).getId());
        else if (indexOfColumn == 1) assertEquals(PRODUCT_NAME, response.get(0).getName());
        else if (indexOfColumn == 2) assertEquals(PRODUCT_CATEGORY, response.get(0).getCategory());
        else assertEquals(Long.valueOf(PRODUCT_PRICE), response.get(0).getPrice());

        System.out.println(
                "RESULT DATA: " + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response)
        );
    }

    @Test
    void testGetOneProductByParam() throws JsonProcessingException {
        var response = productService.getOneByParam(
                2, PRODUCT_CATEGORY, fileReaderService.readAll(BED_LINES_FILE_PATH)
        );

        assertNotNull(response);
        assertTrue(response.isPresent());
        var oneProduct = response.get();
        assertEquals(8L, oneProduct.getId());
        assertEquals("Toilet", oneProduct.getName());
        assertEquals(PRODUCT_CATEGORY, oneProduct.getCategory());
        assertEquals(22L, oneProduct.getPrice());

        System.out.println(
                "RESULT DATA: " + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(oneProduct)
        );
    }

    
    @Test
    void testGetAllProductsBySorting() throws JsonProcessingException {
        var unsortedProducts = productService.findAllProducts();

        var response = productService.getAllSortedProducts(
                "NAME", "DESC", unsortedProducts
        );
        assertNotNull(response);
        assertEquals(43, response.size());
        assertNotEquals(unsortedProducts.get(0).getId(), response.get(0).getId());

        System.out.println(
                "RESULT DATA: " + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response)
        );
    }

    private static List<Arguments> getArguments() {
        return List.of(
                Arguments.of(0, PRODUCT_ID, 1),
                Arguments.of(1, PRODUCT_NAME, 2),
                Arguments.of(2, PRODUCT_CATEGORY, 11),
                Arguments.of(3, PRODUCT_PRICE, 1)
        );
    }
}
