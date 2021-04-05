package com.example.ratingmicroservice.controller.response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

public class RestResponseBuilder<T> {

    private HttpStatus status;
    private String message;
    private T result;

    public RestResponseBuilder<T> status(HttpStatus status) {
        this.status = status;
        return this;
    }

    public RestResponseBuilder<T> exception(ResponseStatusException exception) {
        HttpStatus status = exception.getStatus();
        this.status = status;

        if (!Objects.requireNonNull(exception.getReason()).isBlank()) {
            this.message = exception.getReason();
        }

        return this;
    }

    public RestResponseBuilder<T> message(String message) {
        this.message = message;
        return this;
    }

    public RestResponseBuilder<T> result(T result) {
        this.result = result;
        return this;
    }

    public RestResponse<T> build() {
        RestResponse<T> response = new RestResponse<T>();
        response.setStatus(status);
        response.setMessage(message);
        response.setResult(result);
        return response;
    }

    public ResponseEntity<Object> entity() {
        return ResponseEntity.status(status).headers(HttpHeaders.EMPTY).body(build());
    }
}