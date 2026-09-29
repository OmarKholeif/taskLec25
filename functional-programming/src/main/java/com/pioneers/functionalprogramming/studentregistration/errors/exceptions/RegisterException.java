package com.pioneers.functionalprogramming.studentregistration.errors.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegisterException extends RuntimeException {
    public RegisterException(String message, String logMessage) {
        super(message);
        log.error(logMessage);
    }
}
