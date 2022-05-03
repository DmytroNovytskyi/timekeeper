package com.epam.exception;

import com.epam.timekeeper.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectNotFoundExceptionMessageTest {
    @Test
    public void IncorrectDataExceptionMessageTest() {
        try {
            throw new ObjectNotFoundException("test");
        } catch (ObjectNotFoundException e) {
            assertEquals("test", e.getMessage());
        }
    }
}
