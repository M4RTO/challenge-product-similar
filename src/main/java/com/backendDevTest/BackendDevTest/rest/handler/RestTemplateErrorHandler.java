package com.backendDevTest.BackendDevTest.rest.handler;

import com.backendDevTest.BackendDevTest.exception.RestClientGenericException;
import com.backendDevTest.BackendDevTest.rest.ProductRestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class RestTemplateErrorHandler implements ResponseErrorHandler {

    private final Map<HttpStatus, RuntimeException> exceptionsMap;

    Logger logger = LogManager.getLogger(RestTemplateErrorHandler.class);


    public RestTemplateErrorHandler(Map<HttpStatus, RuntimeException> exceptionsMap) {
        this.exceptionsMap = exceptionsMap;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException, RuntimeException {
        logger.error("There are an error in api response with this message: {}", response);
        throw exceptionsMap.getOrDefault(response.getStatusCode(),
                new RestClientGenericException("Internal Server Exception"));

    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        ResponseErrorHandler.super.handleError(url, method, response);
    }

}
