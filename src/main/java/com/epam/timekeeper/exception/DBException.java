package com.epam.timekeeper.exception;

/**
 * Wrapper exception for the situation when some database error happened.
 */
public class DBException extends RuntimeException {
    public DBException(String message) {
        super(message);
    }
}
