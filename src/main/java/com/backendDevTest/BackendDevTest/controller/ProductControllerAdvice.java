package com.backendDevTest.BackendDevTest.controller;

import com.backendDevTest.BackendDevTest.exception.InternalServerErrorException;
import com.backendDevTest.BackendDevTest.exception.NotFoundProductException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice(assignableTypes = ProductController.class)
public class ProductControllerAdvice {

    public static final String MESSAGE = "message";
    public static final String TIMESTAMP = "timestamp";

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<Object> handleInvalidAuthorization(InternalServerErrorException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(MESSAGE, e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundProductException.class)
    public ResponseEntity<Object> handleInvalidAuthorization(NotFoundProductException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(MESSAGE, e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }


}
