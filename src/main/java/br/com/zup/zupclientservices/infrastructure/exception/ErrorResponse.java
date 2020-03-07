package br.com.zup.zupclientservices.infrastructure.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ErrorResponse {

    //HTTP Status code to facilitate reading of the error
    private HttpStatus status;
    //DateTime of the error
    private LocalDateTime timestamp;
    //General error message about nature of error
    private String message;
    //List of errors with details
    private List<String> errors;

    public ErrorResponse(HttpStatus status, LocalDateTime timestamp, String message, List<String> errors) {
        this.status = status;
        this.timestamp = timestamp;
        this.message = message;
        this.errors = errors;
    }

    public ErrorResponse(HttpStatus status, LocalDateTime timestamp, String message, String error) {
        this.status = status;
        this.timestamp = timestamp;
        this.message = message;
        this.errors = Arrays.asList(error);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
}