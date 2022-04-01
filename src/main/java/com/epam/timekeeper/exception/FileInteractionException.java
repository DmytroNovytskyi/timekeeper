package com.epam.timekeeper.exception;

/**
 * Wrapper exception for the situation when MySQLConnector class
 * tries to interact with application.properties file but something goes wrong.
 */
public class FileInteractionException extends RuntimeException{
    public FileInteractionException(String message){
        super(message);
    }
}
