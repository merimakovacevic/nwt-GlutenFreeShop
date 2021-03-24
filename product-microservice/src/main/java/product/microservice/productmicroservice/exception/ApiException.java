package product.microservice.productmicroservice.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

    public ApiException(String meesage, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.message = meesage;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}