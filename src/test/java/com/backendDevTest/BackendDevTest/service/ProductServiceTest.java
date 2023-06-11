package com.backendDevTest.BackendDevTest.service;

import com.backendDevTest.BackendDevTest.MockFactory;
import com.backendDevTest.BackendDevTest.controller.model.response.ProductModelResponse;
import com.backendDevTest.BackendDevTest.exception.InternalServerErrorException;
import com.backendDevTest.BackendDevTest.exception.NotFoundProductException;
import com.backendDevTest.BackendDevTest.rest.ProductRestController;
import com.backendDevTest.BackendDevTest.rest.ProductSimilarRestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Product Service Test")
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    private final ProductRestController productRestController = mock(ProductRestController.class);
    private final ProductSimilarRestController productSimilarRestController = mock(ProductSimilarRestController.class);
    private static final ThreadPoolTaskExecutor executor = MockFactory.get();
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void getSimilarProductsNotFound() {
        final ProductService productService = new ProductService(productRestController,productSimilarRestController,executor);

        when(productRestController.getDetailProduct(any())).thenThrow(NotFoundProductException.class);
        when(productSimilarRestController.getSimilarProducts(any())).thenReturn(new String[] {"3", "1", "4"});


        CompletableFuture<List<ProductModelResponse>> similarProduct = productService.getSimilarProduct("1");

        final CompletionException e = assertThrows(CompletionException.class, similarProduct::join);
        assertEquals(NotFoundProductException.class, e.getCause().getClass());
    }

    @Test
    public void getSimilarProductsInternal() {
        final ProductService productService = new ProductService(productRestController,productSimilarRestController,executor);

        when(productRestController.getDetailProduct(any())).thenThrow(NotFoundProductException.class);
        when(productSimilarRestController.getSimilarProducts(any())).thenReturn(new String[] {"3", "1", "4"});


        CompletableFuture<List<ProductModelResponse>> similarProduct = productService.getSimilarProduct("1");

        final CompletionException e = assertThrows(CompletionException.class, similarProduct::join);
        assertEquals(NotFoundProductException.class, e.getCause().getClass());
    }


    @Test
    public void getSimilarProductsOK() throws JsonProcessingException {
        final ProductService productService = new ProductService(productRestController,productSimilarRestController,executor);

        when(productRestController.getDetailProduct("3")).thenReturn(Optional.of(MockFactory.getProductDetail(3L)));
        when(productRestController.getDetailProduct("1")).thenReturn(Optional.of(MockFactory.getProductDetail(1L)));
        when(productRestController.getDetailProduct("4")).thenReturn(Optional.of(MockFactory.getProductDetail(4L)));
        when(productSimilarRestController.getSimilarProducts(any())).thenReturn(new String[] {"3", "1", "4"});

        final var expected = objectMapper.writeValueAsString(MockFactory.getSimilarProducts(new Long[] {3L, 1L, 4L}));

        CompletableFuture<List<ProductModelResponse>> similarProduct = productService.getSimilarProduct("1");
        final var actual = objectMapper.writeValueAsString(similarProduct.join());

        assertEquals(expected, actual);

    }
}
