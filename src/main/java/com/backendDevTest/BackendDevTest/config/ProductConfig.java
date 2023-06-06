package com.backendDevTest.BackendDevTest.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("api-product-config")
public class ProductConfig {

    @Data
    public static class ProductRepository {
        public static final String SIMILARIDS = "/similarids";

        @NotBlank
        private String urlBase;

        public String getProductDetailUrl() {
            return urlBase;
        }

        public String getProductSimilarUrl() {
            return urlBase.concat(SIMILARIDS);
        }
    }




}
