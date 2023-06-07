package com.backendDevTest.BackendDevTest.rest;

import com.backendDevTest.BackendDevTest.config.CacheConfig;
import com.backendDevTest.BackendDevTest.config.ProductConfig;
import com.backendDevTest.BackendDevTest.exception.InternalServerErrorException;
import com.backendDevTest.BackendDevTest.exception.NotFoundProductException;
import com.backendDevTest.BackendDevTest.rest.handler.RestTemplateErrorHandler;
import com.backendDevTest.BackendDevTest.rest.model.ProductDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProductRestController implements ProductRestControllerImpl {
    Logger logger = LogManager.getLogger(ProductRestController.class);

    private final RestTemplate restTemplate;
    private final ProductConfig.ProductRepository config;

    @Autowired
    public ProductRestController(RestTemplate restTemplate, ProductConfig productConfig) {
        this.restTemplate = restTemplate;
        this.config = productConfig.getProductRepository();
        final RestTemplateErrorHandler errorHandler = new RestTemplateErrorHandler(
                Map.of(
                        HttpStatus.NOT_FOUND, new NotFoundProductException("Not Found Product Exception"),
                        HttpStatus.INTERNAL_SERVER_ERROR, new InternalServerErrorException("Internal server error Exception")
                )
        );
        this.restTemplate.setErrorHandler(errorHandler);
    }

    @Override
    @Cacheable(value = CacheConfig.PRODUCT_ID_DETAIL_CACHE, key = "#id")
    public ProductDetailResponse getDetailProduct(String id) {
        logger.info("Finding product detail with id: {}", id );
        ResponseEntity<ProductDetailResponse> exchange;
             exchange = this.restTemplate.getForEntity(
                    String.format(this.config.getProductDetailUrl(), id),
                    ProductDetailResponse.class);
        return Optional.of(exchange)
                    .map(ResponseEntity::getBody)
                    .orElseThrow(() -> new InternalServerErrorException("No body content - Internal Error"));

    }

    @Override
    @Cacheable(value = CacheConfig.PRODUCT_ID_SIMILAR_CACHE, key = "#id")
    public Long[] getSimilarProducts(String id) {
        logger.info("Finding product similar with id: {}", id );
        ResponseEntity<Long[]> exchange = this.restTemplate.getForEntity(
                String.format(this.config.getProductSimilarUrl(), id),
                Long[].class);
        return Optional.of(exchange)
                .map(ResponseEntity::getBody)
                .orElseThrow(() -> new InternalServerErrorException("No body content - Internal Error"));
    }
}
