package com.backendDevTest.BackendDevTest.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;


@Data
@Validated
@Configuration
@ConfigurationProperties("api-product-config")
public class ProductConfig {

    private ProductRepository productRepository;
    private ProductCache productCache;
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

    @Data
    public static class ProductCache {
        @NotBlank
        private String keyPrefixProductDetail;
        @NotBlank
        private String keyPrefixProductSimilar;
        @NotNull
        private Long ttl;
    }





}
