package com.backendDevTest.BackendDevTest.controller.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductModelResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private Boolean availability;
}
