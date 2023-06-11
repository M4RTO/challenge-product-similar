package com.backendDevTest.BackendDevTest.rest;

import com.backendDevTest.BackendDevTest.config.CacheConfig;
import com.backendDevTest.BackendDevTest.config.ProductConfig;
import com.backendDevTest.BackendDevTest.exception.InternalServerErrorException;
import com.backendDevTest.BackendDevTest.exception.NotFoundProductException;
import com.backendDevTest.BackendDevTest.rest.handler.RestTemplateErrorHandler;
import com.backendDevTest.BackendDevTest.rest.model.ProductDetailResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Repository
public class ProductSimilarRestController implements ProductSimilarRestControllerImpl {
    Logger logger = LogManager.getLogger(ProductSimilarRestController.class);
    private final RestTemplate restTemplate;
    private final ProductConfig.ProductRepository config;

    @Autowired
    public ProductSimilarRestController(RestTemplate restTemplate, ProductConfig productConfig) {
        final RestTemplateErrorHandler errorHandler = new RestTemplateErrorHandler(
                Map.of(
                        HttpStatus.NOT_FOUND, new NotFoundProductException("Not Found Product Exception"),
                        HttpStatus.INTERNAL_SERVER_ERROR, new InternalServerErrorException("Internal server error Exception")
                )
        );
        this.restTemplate = restTemplate;
        this.config = productConfig.getProductRepository();
        this.restTemplate.setErrorHandler(errorHandler);
    }

    @Override
   // @Cacheable(value = CacheConfig.PRODUCT_ID_SIMILAR_CACHE, key = "#id")
    public String[] getSimilarProducts(String id) {
        logger.info("Finding product similar with id: {}", id );
        try{
            ResponseEntity<String[]> exchange = this.restTemplate.getForEntity(
                    String.format(this.config.getProductSimilarUrl(), id),
                    String[].class);
            return Optional.of(exchange)
                    .map(ResponseEntity::getBody)
                    .orElseThrow(() -> new InternalServerErrorException("No body content - Internal Error"));
        }catch (Exception e) {
            logger.error("There are an error in api response with this exception: {}", e);
            throw e;
        }


    }
}
