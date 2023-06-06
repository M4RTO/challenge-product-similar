package com.backendDevTest.BackendDevTest.rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductDetailResponse {

    private String id;
    private String name;
    private Integer price;
    private Boolean availability;
}
