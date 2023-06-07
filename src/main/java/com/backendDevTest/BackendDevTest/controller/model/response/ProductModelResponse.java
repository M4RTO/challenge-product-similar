package com.backendDevTest.BackendDevTest.controller.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class ProductModelResponse {

    private Integer id;
    private String name;
    private Integer price;
    private Boolean availability;
}
