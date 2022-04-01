package com.epam.timekeeper.exception;

/**
 * Wrapper exception for the situation when mapper tries to create
 * data transfer object but could not find corresponding data in database.
 */
public class DTOConversionException extends RuntimeException {
    public DTOConversionException(String message){super(message);}
}
