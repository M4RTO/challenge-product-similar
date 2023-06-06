package com.backendDevTest.BackendDevTest.rest.handler;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class RestTemplateErrorHandler implements ResponseErrorHandler {

    private final Map<HttpStatus, RuntimeException> exceptionsMap;

    public RestTemplateErrorHandler(Map<HttpStatus, RuntimeException> exceptionsMap) {
        this.exceptionsMap = exceptionsMap;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) {
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        ResponseErrorHandler.super.handleError(url, method, response);
    }

}
