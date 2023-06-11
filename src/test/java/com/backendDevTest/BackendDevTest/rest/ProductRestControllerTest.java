package com.backendDevTest.BackendDevTest.rest;

import com.backendDevTest.BackendDevTest.MockFactory;
import com.backendDevTest.BackendDevTest.config.TestConfig;
import com.backendDevTest.BackendDevTest.rest.model.ProductDetailResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.net.URISyntaxException;
import java.util.Optional;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withResourceNotFound;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@Import(TestConfig.class)
@RestClientTest({ProductRestController.class})
public class ProductRestControllerTest {

    @Autowired
    private ProductRestController productRestController;
    @Autowired
    private MockRestServiceServer mockServer;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void callRestAndResponseOk() throws JsonProcessingException {
        Optional<ProductDetailResponse> detailResponse = Optional.of(MockFactory.getProductDetail(1L));

        this.mockServer.expect(requestTo("http://some-host/product"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(detailResponse), MediaType.APPLICATION_JSON));


        Optional<ProductDetailResponse> productDetailResponse = this.productRestController.getDetailProduct("1");
        Assertions.assertEquals(productDetailResponse, detailResponse);
    }

    @Test
    public void callRestProductDetailAndResponseNotFound() throws JsonProcessingException, URISyntaxException {

        this.mockServer.expect(requestTo("http://some-host/product"))
                .andRespond(withResourceNotFound());


        Optional<ProductDetailResponse> productDetailResponse = this.productRestController.getDetailProduct("1");
        Assertions.assertEquals(productDetailResponse, Optional.empty());


    }

}
