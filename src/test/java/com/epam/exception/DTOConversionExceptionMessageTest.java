package com.epam.exception;

import com.epam.timekeeper.exception.DTOConversionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DTOConversionExceptionMessageTest {
    @Test
    public void IncorrectDataExceptionMessageTest() {
        try {
            throw new DTOConversionException("test");
        } catch (DTOConversionException e) {
            assertEquals("test", e.getMessage());
        }
    }
}
