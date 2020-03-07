package br.com.zup.zupclientservices.domain.exception;

import org.springframework.validation.FieldError;

public class ClientValidationException extends RuntimeException {

    private static final String OBJECT_NAME = "ClientResource";

    FieldError fieldError;

    public ClientValidationException(String field, String message) {
        super(OBJECT_NAME + " is invalid");
        this.fieldError = new FieldError(OBJECT_NAME, field, message);
    }

    public FieldError getFieldError() {
        return fieldError;
    }
}
