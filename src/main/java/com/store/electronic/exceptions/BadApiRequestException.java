package com.store.electronic.exceptions;

public class BadApiRequestException extends RuntimeException {

    public BadApiRequestException() {
        super("Bad API request");
    }

    public BadApiRequestException(String message) {
        super(message);
    }
}
