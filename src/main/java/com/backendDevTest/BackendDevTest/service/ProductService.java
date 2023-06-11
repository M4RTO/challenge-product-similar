package com.backendDevTest.BackendDevTest.service;

import com.backendDevTest.BackendDevTest.controller.model.response.ProductModelResponse;
import com.backendDevTest.BackendDevTest.rest.ProductRestController;
import com.backendDevTest.BackendDevTest.rest.ProductSimilarRestController;
import com.backendDevTest.BackendDevTest.rest.model.ProductDetailResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
public class ProductService implements ProductServiceImpl {

    private final ProductRestController productRestController;
    private final ProductSimilarRestController productRestSimilarController;
    private final Executor executor;

    Logger logger = LogManager.getLogger(ProductService.class);

    @Autowired
    public ProductService(ProductRestController productRestController,
                          ProductSimilarRestController productRestSimilarController,
                          Executor executor) {
        this.productRestController = productRestController;
        this.productRestSimilarController = productRestSimilarController;
        this.executor = executor;
    }

    @Override
    public CompletableFuture<List<ProductModelResponse>> getSimilarProduct(String id) {
        return CompletableFuture.supplyAsync(() -> {
            List<ProductModelResponse> productModelResponseList = new ArrayList<>();
            String[] similarProducts = productRestSimilarController.getSimilarProducts(id);

            Arrays.stream(similarProducts)
                    .map(productRestController::getDetailProduct)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(detailProduct -> new ProductModelResponse(
                            detailProduct.getId(),
                            detailProduct.getName(),
                            detailProduct.getPrice(),
                            detailProduct.getAvailability()))
                    .forEach(productModelResponseList::add);

            return productModelResponseList;
        }, executor);
}
}
