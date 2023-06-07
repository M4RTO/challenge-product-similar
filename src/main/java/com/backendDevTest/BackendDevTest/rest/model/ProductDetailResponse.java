package com.backendDevTest.BackendDevTest.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@Data
public class ProductDetailResponse implements Serializable {

    private Long id;
    private String name;
    private BigDecimal price;
    private Boolean availability;
}
