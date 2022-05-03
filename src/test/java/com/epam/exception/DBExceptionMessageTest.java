package com.epam.exception;

import com.epam.timekeeper.exception.DBException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DBExceptionMessageTest {
    @Test
    public void IncorrectDataExceptionMessageTest() {
        try {
            throw new DBException("test");
        } catch (DBException e) {
            assertEquals("test", e.getMessage());
        }
    }
}
