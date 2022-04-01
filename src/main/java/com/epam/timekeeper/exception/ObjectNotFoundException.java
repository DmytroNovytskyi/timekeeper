package com.epam.timekeeper.exception;

/**
 * Wrapper exception for the situation when user tries to change
 * update database record, but it was deleted after request.
 */
public class ObjectNotFoundException extends RuntimeException{
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
