package com.codeit.backend.exception;

public class MissingJsonDataException extends RuntimeException {
    public MissingJsonDataException(String message) {
        super(message);
    }
}
