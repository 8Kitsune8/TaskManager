package com.minitask.taskmanager.exception;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadTaskFormatException extends RuntimeException {

    public BadTaskFormatException(Errors errors) {
        super(Objects.requireNonNull(errors.getFieldError()).getField() + " " + errors.getFieldError().getDefaultMessage());
    }
    public BadTaskFormatException(){

    }
}
