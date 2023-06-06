package com.backendDevTest.BackendDevTest.rest;

import com.backendDevTest.BackendDevTest.config.ProductConfig;
import com.backendDevTest.BackendDevTest.exception.InternalServerErrorException;
import com.backendDevTest.BackendDevTest.exception.NotFoundProductException;
import com.backendDevTest.BackendDevTest.rest.handler.RestTemplateErrorHandler;
import com.backendDevTest.BackendDevTest.rest.model.ProductDetailResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProductRestController implements ProductRestControllerImpl {

    private RestTemplate restTemplate;
    private ProductConfig.ProductRepository config;

    public ProductRestController(RestTemplate restTemplate) {

        final RestTemplateErrorHandler errorHandler = new RestTemplateErrorHandler(
                Map.of(
                        HttpStatus.NOT_FOUND, new NotFoundProductException("Not Found Product Exception"),
                        HttpStatus.INTERNAL_SERVER_ERROR, new InternalServerErrorException("Internal server error Exception")
                )
        );
        restTemplate.setErrorHandler(errorHandler);

    }

    @Override
    public ProductDetailResponse getDetailProduct(String id) {
        ResponseEntity<ProductDetailResponse> exchange = this.restTemplate.exchange(
                String.format(this.config.getProductDetailUrl(), id),
                HttpMethod.GET,
                null,
                ProductDetailResponse.class);

        return Optional.of(exchange)
                .map(ResponseEntity::getBody)
                .orElseThrow(() -> new InternalServerErrorException("No body content - Internal Error"));

    }

    @Override
    public Integer[] getSimilarProducts(String id) {
        ResponseEntity<Integer[]> exchange = this.restTemplate.exchange(
                String.format(this.config.getProductSimilarUrl(), id),
                HttpMethod.GET,
                null,
                Integer[].class);

        return Optional.of(exchange)
                .map(ResponseEntity::getBody)
                .orElseThrow(() -> new InternalServerErrorException("No body content - Internal Error"));
    }
}
