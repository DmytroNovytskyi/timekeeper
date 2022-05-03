package com.epam.exception;

import com.epam.timekeeper.exception.FileInteractionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileInteractionExceptionMessageTest {
    @Test
    public void IncorrectDataExceptionMessageTest() {
        try {
            throw new FileInteractionException("test");
        } catch (FileInteractionException e) {
            assertEquals("test", e.getMessage());
        }
    }
}
