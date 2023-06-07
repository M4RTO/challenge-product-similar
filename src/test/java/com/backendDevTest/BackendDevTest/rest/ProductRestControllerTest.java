package com.backendDevTest.BackendDevTest.rest;

import com.backendDevTest.BackendDevTest.MockFactory;
import com.backendDevTest.BackendDevTest.config.TestConfig;
import com.backendDevTest.BackendDevTest.exception.InternalServerErrorException;
import com.backendDevTest.BackendDevTest.exception.NotFoundProductException;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;


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
        ProductDetailResponse detailResponse = MockFactory.getProductDetail();

        this.mockServer.expect(requestTo("http://some-host/product"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(detailResponse), MediaType.APPLICATION_JSON));


        ProductDetailResponse productDetailResponse = this.productRestController.getDetailProduct("1");
        Assertions.assertEquals(productDetailResponse, detailResponse);
    }

    @Test
    public void callRestProductDetailAndResponseNotFound() throws JsonProcessingException, URISyntaxException {

        this.mockServer.expect(requestTo("http://some-host/product"))
                .andRespond(withResourceNotFound());


        Throwable thrown = catchThrowable(() -> this.productRestController.getDetailProduct("1"));

        assertThat(thrown)
                .isInstanceOf(NotFoundProductException.class)
                .hasMessage("Not Found Product Exception");

    }

    @Test
    public void callRestProductDetailAndResponseInternalException() {

        this.mockServer.expect(requestTo("http://some-host/product"))
                .andRespond(withServerError());


        Throwable thrown = catchThrowable(() -> this.productRestController.getDetailProduct("1"));

        assertThat(thrown)
                .isInstanceOf(InternalServerErrorException.class)
                .hasMessage("Internal server error Exception");

    }


    @Test
    public void callRestProductSimilarAndResponseOk() throws JsonProcessingException {
        Long[] expected = new Long[] {3L, 1L, 4L};

        this.mockServer.expect(requestTo("http://some-host/product".concat("/similarids")))
                .andRespond(withSuccess(objectMapper.writeValueAsString(expected), MediaType.APPLICATION_JSON));


        Long[] result = this.productRestController.getSimilarProducts("1");

        Assertions.assertArrayEquals(expected,result);
    }

    @Test
    public void callRestProductSimilarAndResponseNotFound() {

        this.mockServer.expect(requestTo("http://some-host/product".concat("/similarids")))
                .andRespond(withResourceNotFound());


        Throwable thrown = catchThrowable(() -> this.productRestController.getSimilarProducts("1"));

        assertThat(thrown)
                .isInstanceOf(NotFoundProductException.class)
                .hasMessage("Not Found Product Exception");

    }

    @Test
    public void callRestProductSimilarAndResponseInternalException() {

        this.mockServer.expect(requestTo("http://some-host/product".concat("/similarids")))
                .andRespond(withServerError());


        Throwable thrown = catchThrowable(() -> this.productRestController.getSimilarProducts("1"));

        assertThat(thrown)
                .isInstanceOf(InternalServerErrorException.class)
                .hasMessage("Internal server error Exception");

    }

}
