package com.shire42.backend.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIdUtils {

    @Test
    public void testCodeGenerateWithEightPositions() {
        String code = IdUtils.generateId();
        assertEquals(8, code.length());
    }

}
