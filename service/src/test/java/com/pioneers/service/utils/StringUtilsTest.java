package com.pioneers.service.utils;

import org.junit.jupiter.api.Test;

import static com.pioneers.service.utils.StringUtils.isNullOrBlank;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringUtilsTest {

    @Test
    public void testIsNullOrBlank_whenPassingNullString() {
        assertTrue(isNullOrBlank(null));
    }

    @Test
    public void testIsNullOrBlank_whenPassingBlankString() {
        assertTrue(isNullOrBlank("    "));
    }

    @Test
    public void testIsNullOrBlank_whenPassingStringContainsText() {
        assertFalse(isNullOrBlank("omar"));
    }
}
