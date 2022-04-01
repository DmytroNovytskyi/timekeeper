package com.epam.timekeeper.exception;

/**
 * Wrapper exception for the situation when user tries to change
 * activity status to be OPENED, but category it's related to, has status CLOSED.
 */
public class ActivityOpenException extends RuntimeException {
    public ActivityOpenException(String message) {
        super(message);
    }
}
