package com.temporal.demos.temporalspringbootdemo.exception;

public class NonRetryException extends RuntimeException {

    public NonRetryException(String message) {
        super(message);
    }
}
