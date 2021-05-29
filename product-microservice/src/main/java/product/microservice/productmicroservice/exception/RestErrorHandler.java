package product.microservice.productmicroservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import product.microservice.productmicroservice.controller.response.RestResponse;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestResponseException.class)
    ResponseEntity<?> handleRestResponseException(RestResponseException ex) {
        return RestResponse.builder()
                .status(ex.getStatus())
                .message(ex.getResponseMessage())
                .entity();
    }

    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<?> handleNoSuchElementException(NoSuchElementException ex) {
        return RestResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .entity();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        return RestResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .entity();
    }

    protected @Override
    ResponseEntity handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return RestResponse.builder()
                .status(status)
                .message(ex.getMessage())
                .entity();
    }

    protected @Override
    ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return RestResponse.builder()
                .status(status)
                .message(ex.getMessage())
                .entity();
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<?> handleAllExceptions(Exception ex, WebRequest request) {
        return handleEveryException(ex, request);
    }

    protected ResponseEntity<?> handleEveryException(Exception ex, WebRequest request) {
        return RestResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ex.getMessage())
                .entity();
    }
}