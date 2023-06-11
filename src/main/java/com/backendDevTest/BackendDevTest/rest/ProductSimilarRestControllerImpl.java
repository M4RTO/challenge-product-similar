package com.backendDevTest.BackendDevTest.rest;

import com.backendDevTest.BackendDevTest.rest.model.ProductDetailResponse;

import java.util.Optional;


public interface ProductSimilarRestControllerImpl {

     String[] getSimilarProducts(String id);
}
