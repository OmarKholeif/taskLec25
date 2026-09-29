package com.pioneers.functionalprogramming.studentregistration.errors.handlers;

import com.pioneers.functionalprogramming.studentregistration.errors.exceptions.RegisterException;
import com.pioneers.functionalprogramming.studentregistration.errors.models.ErrorResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StudentHandler {

    @ExceptionHandler(exception = RegisterException.class)
    public ErrorResponse handleRegisterException(final RegisterException e) {
        return new ErrorResponse("registeration error", e.getMessage());
    }
}
