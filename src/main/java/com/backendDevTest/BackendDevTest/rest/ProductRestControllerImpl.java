package com.backendDevTest.BackendDevTest.rest;

import com.backendDevTest.BackendDevTest.rest.model.ProductDetailResponse;

import java.util.Optional;


public interface ProductRestControllerImpl {

     Optional<ProductDetailResponse> getDetailProduct(String id);
}
