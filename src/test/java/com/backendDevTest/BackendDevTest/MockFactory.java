package com.backendDevTest.BackendDevTest;

import com.backendDevTest.BackendDevTest.controller.model.response.ProductModelResponse;
import com.backendDevTest.BackendDevTest.rest.model.ProductDetailResponse;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockFactory {


    private static final int CORE_POOL_SIZE = 20;
    private static final int MAX_POOL_SIZE = 1000;
    private static final String ASYNC_PREFIX = "async-";
    private static final boolean WAIT_FOR_TASK_TO_COMPLETE_ON_SHUTDOWN = true;


    public static List<ProductModelResponse> getProducts() {
        return List.of(
                new ProductModelResponse(1L,"T-Shirt",new BigDecimal("10.00"),true),
                new ProductModelResponse(2L,"T-Shirt",new BigDecimal("10.00"),true),
                new ProductModelResponse(3L,"T-Shirt",new BigDecimal("10.00"),true)
        );
    }

    public static ProductDetailResponse getProductDetail() {
        return new ProductDetailResponse(1L,"T-shirt",new BigDecimal("10.00"),true);
    }

    public static List<ProductModelResponse> getSimilarProducts(Long[] longs) {
        List<ProductModelResponse> productModelResponseList = new ArrayList<>();

        Arrays.stream(longs).toList().forEach(idProduct -> {
            ProductModelResponse productModelResponse = new ProductModelResponse(
                    idProduct,
                    getProductDetail().getName(),
                    getProductDetail().getPrice(),
                    getProductDetail().getAvailability());
            productModelResponseList.add(productModelResponse);
        });
        return productModelResponseList;
    }

    public static ThreadPoolTaskExecutor get() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setWaitForTasksToCompleteOnShutdown(WAIT_FOR_TASK_TO_COMPLETE_ON_SHUTDOWN);
        executor.setThreadNamePrefix(ASYNC_PREFIX);
        executor.initialize();
        return executor;
    }
}
