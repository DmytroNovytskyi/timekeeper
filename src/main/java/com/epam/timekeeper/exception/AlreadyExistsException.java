package com.epam.timekeeper.exception;

/**
 * Wrapper exception for the situation when user tries to create
 * new record in database, but UNIQUE key does not let it happen.
 */
public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}
