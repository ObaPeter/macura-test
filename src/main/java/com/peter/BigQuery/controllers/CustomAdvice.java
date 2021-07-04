package com.peter.BigQuery.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomAdvice {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<String> handleException(Exception ex,
                                                        WebRequest request) {

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(ex.getMessage() + "::" + request.getDescription(true));

    }
}
