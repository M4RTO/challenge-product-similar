package com.backendDevTest.BackendDevTest.controller;

import com.backendDevTest.BackendDevTest.MockFactory;
import com.backendDevTest.BackendDevTest.exception.InternalServerErrorException;
import com.backendDevTest.BackendDevTest.exception.NotFoundProductException;
import com.backendDevTest.BackendDevTest.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Product Controller Adapter Tests")
@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {

    private static final String URL = "/product/{id}/similar";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("when getProductsSimilar is called, the controller should return the similar product associated to the id")
    void getProductOk() throws Exception {
        final var expected = objectMapper.writeValueAsString(MockFactory.getProducts());

        when(productService.getSimilarProduct(any()))
                .thenReturn(MockFactory.getProducts());

        mockMvc.perform(get(URL,"1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));

    }

    @Test
    @DisplayName("when getProductsSimilar is called, the controller should return Not found with 404 Not Found")
    void getProductNotOk() throws Exception {

        when(productService.getSimilarProduct(any()))
                .thenThrow(NotFoundProductException.class);

        mockMvc.perform(get(URL,"1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
