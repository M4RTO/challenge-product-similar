package com.backendDevTest.BackendDevTest.rest;

import com.backendDevTest.BackendDevTest.config.TestConfig;
import com.backendDevTest.BackendDevTest.exception.InternalServerErrorException;
import com.backendDevTest.BackendDevTest.exception.NotFoundProductException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@Import(TestConfig.class)
@RestClientTest({ProductSimilarRestController.class})
public class ProductSimilarRestControllerTest {

    @Autowired
    private ProductSimilarRestController productSimilarRestController;
    @Autowired
    private MockRestServiceServer mockServer;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void callRestProductSimilarAndResponseOk() throws JsonProcessingException {
        String[] expected = new String[] {"3", "1", "4"};

        this.mockServer.expect(requestTo("http://some-host/product".concat("/similarids")))
                .andRespond(withSuccess(objectMapper.writeValueAsString(expected), MediaType.APPLICATION_JSON));


        String[] result = this.productSimilarRestController.getSimilarProducts("1");

        Assertions.assertArrayEquals(expected,result);
    }

    @Test
    public void callRestProductSimilarAndResponseNotFound() {

        this.mockServer.expect(requestTo("http://some-host/product".concat("/similarids")))
                .andRespond(withResourceNotFound());


        Throwable thrown = catchThrowable(() -> this.productSimilarRestController.getSimilarProducts("1"));

        assertThat(thrown)
                .isInstanceOf(NotFoundProductException.class)
                .hasMessage("Not Found Product Exception");

    }

    @Test
    public void callRestProductSimilarAndResponseInternalException() {

        this.mockServer.expect(requestTo("http://some-host/product".concat("/similarids")))
                .andRespond(withServerError());


        Throwable thrown = catchThrowable(() -> this.productSimilarRestController.getSimilarProducts("1"));

        assertThat(thrown)
                .isInstanceOf(InternalServerErrorException.class)
                .hasMessage("Internal server error Exception");

    }
}
