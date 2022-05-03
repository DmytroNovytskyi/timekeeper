package com.epam.exception;

import com.epam.timekeeper.exception.AlreadyExistsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlreadyExistsExceptionMessageTest {
    @Test
    public void IncorrectDataExceptionMessageTest() {
        try {
            throw new AlreadyExistsException("test");
        } catch (AlreadyExistsException e) {
            assertEquals("test", e.getMessage());
        }
    }
}
