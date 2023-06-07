package com.backendDevTest.BackendDevTest.controller;

import com.backendDevTest.BackendDevTest.controller.model.response.ProductModelResponse;
import com.backendDevTest.BackendDevTest.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    Logger logger = LogManager.getLogger(ProductController.class);


    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}/similar")
    public CompletableFuture<List<ProductModelResponse>> getProducts(@PathVariable String id){
        logger.info("Finding similar product and detail with id: {}", id );
        return productService.getSimilarProduct(id).thenApply( p -> p);
    }
}
