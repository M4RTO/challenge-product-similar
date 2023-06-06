package com.backendDevTest.BackendDevTest.service;

import com.backendDevTest.BackendDevTest.controller.model.response.ProductModelResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductServiceImpl {

    List<ProductModelResponse> getSimilarProduct(String id);
}
