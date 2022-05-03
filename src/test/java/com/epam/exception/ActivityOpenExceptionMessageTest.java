package com.epam.exception;

import com.epam.timekeeper.exception.ActivityOpenException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActivityOpenExceptionMessageTest {
    @Test
    public void IncorrectDataExceptionMessageTest() {
        try {
            throw new ActivityOpenException("test");
        } catch (ActivityOpenException e) {
            assertEquals("test", e.getMessage());
        }
    }
}
