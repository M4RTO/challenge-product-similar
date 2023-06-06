package com.backendDevTest.BackendDevTest.service;

import com.backendDevTest.BackendDevTest.controller.model.response.ProductModelResponse;
import com.backendDevTest.BackendDevTest.rest.ProductRestController;
import com.backendDevTest.BackendDevTest.rest.model.ProductDetailResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductService implements ProductServiceImpl {

    private ProductRestController productRestController;

    @Override
    public List<ProductModelResponse> getSimilarProduct(String id) {
        ProductDetailResponse detailProduct = productRestController.getDetailProduct(id);
        Integer[] similarProducts = productRestController.getSimilarProducts(id);
        return null;
    }
}
