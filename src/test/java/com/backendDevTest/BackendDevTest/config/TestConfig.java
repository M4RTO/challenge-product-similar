package com.backendDevTest.BackendDevTest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@TestConfiguration
public class TestConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder,
                                        @Value("${rest.client.default.timeout}") int timeout) {

        return restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(timeout))
                .setReadTimeout(Duration.ofMillis(timeout))
                .build();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    ProductConfig getProductConfig() {
        final var productConfig = new ProductConfig.ProductRepository();
        final var productCache = new ProductConfig.ProductCache();
        productCache.setKeyPrefixProductDetail("product-detail-cache:");
        productCache.setKeyPrefixProductSimilar("product-similar-cache:");
        productConfig.setUrlBase("http://some-host/product");

        final var config = new ProductConfig();
        config.setProductCache(productCache);
        config.setProductRepository(productConfig);
        return config;
    }



}
