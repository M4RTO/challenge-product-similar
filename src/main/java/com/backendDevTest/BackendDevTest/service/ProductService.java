package com.backendDevTest.BackendDevTest.service;

import com.backendDevTest.BackendDevTest.controller.ProductController;
import com.backendDevTest.BackendDevTest.controller.model.response.ProductModelResponse;
import com.backendDevTest.BackendDevTest.rest.ProductRestController;
import com.backendDevTest.BackendDevTest.rest.model.ProductDetailResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
public class ProductService implements ProductServiceImpl {

    private final ProductRestController productRestController;
    private final Executor executor;

    Logger logger = LogManager.getLogger(ProductService.class);

    @Autowired
    public ProductService(ProductRestController productRestController, Executor executor) {
        this.productRestController = productRestController;
        this.executor = executor;
    }

    @Override
    public CompletableFuture<List<ProductModelResponse>> getSimilarProduct(String id) {
        return CompletableFuture.supplyAsync(() -> {
            List<ProductModelResponse> productModelResponseList = new ArrayList<>();
            ProductDetailResponse detailProduct = this.productRestController.getDetailProduct(id);
            Long[] similarProducts = this.productRestController.getSimilarProducts(id);

            logger.info("Finding detail product {}",detailProduct );
            logger.info("Finding similar products {}", Arrays.stream(similarProducts).toList() );


            Arrays.stream(similarProducts).toList().forEach(idProduct -> {
                ProductModelResponse productModelResponse = new ProductModelResponse(
                        idProduct,
                        detailProduct.getName(),
                        detailProduct.getPrice(),
                        detailProduct.getAvailability());
                productModelResponseList.add(productModelResponse);
            });
            return productModelResponseList;
        }, executor);
    }
}
