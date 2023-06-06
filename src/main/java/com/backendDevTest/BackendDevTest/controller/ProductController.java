package com.backendDevTest.BackendDevTest.controller;

import com.backendDevTest.BackendDevTest.controller.model.response.ProductModelResponse;
import com.backendDevTest.BackendDevTest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}/similar")
    public List<ProductModelResponse> getProducts(@PathVariable String id){
        return productService.getSimilarProduct(id);
    }
}
