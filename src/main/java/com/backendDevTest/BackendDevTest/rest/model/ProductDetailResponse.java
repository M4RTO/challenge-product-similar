package com.backendDevTest.BackendDevTest.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Data
public class ProductDetailResponse implements Serializable {

    private String id;
    private String name;
    private Integer price;
    private Boolean availability;
}
