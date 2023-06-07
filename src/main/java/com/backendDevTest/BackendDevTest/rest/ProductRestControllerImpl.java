package com.backendDevTest.BackendDevTest.rest;

import com.backendDevTest.BackendDevTest.rest.model.ProductDetailResponse;


public interface ProductRestControllerImpl {

     ProductDetailResponse getDetailProduct(String id);
     Long[] getSimilarProducts(String id);
}
