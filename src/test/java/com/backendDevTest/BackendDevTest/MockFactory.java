package com.backendDevTest.BackendDevTest;

import com.backendDevTest.BackendDevTest.controller.model.response.ProductModelResponse;

import java.math.BigDecimal;
import java.util.List;

public class MockFactory {

    public static List<ProductModelResponse> getProducts() {
        return List.of(
                new ProductModelResponse(1L,"T-Shirt",new BigDecimal("10.00"),true),
                new ProductModelResponse(2L,"T-Shirt",new BigDecimal("10.00"),true),
                new ProductModelResponse(3L,"T-Shirt",new BigDecimal("10.00"),true)
        );
    }

}
