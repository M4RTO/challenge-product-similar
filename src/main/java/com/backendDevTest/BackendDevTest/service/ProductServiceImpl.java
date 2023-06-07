package com.backendDevTest.BackendDevTest.service;

import com.backendDevTest.BackendDevTest.controller.model.response.ProductModelResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public interface ProductServiceImpl {

    CompletableFuture<List<ProductModelResponse>> getSimilarProduct(String id);
}
