package com.pioneers.service.utils;

import com.pioneers.service.errors.exceptions.CredentialsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CredentialsHelperTest {

    @Test
    void testHashPassword_shouldThrowExceptionWhenPasswordIsNull() {
        CredentialsException exception = assertThrows(CredentialsException.class, () -> CredentialsHelper.hashPassword(null));

        assertEquals("Password cannot be null", exception.getMessage());
    }

    @Test
    void shouldHashPassword() throws CredentialsException {
        String result = CredentialsHelper.hashPassword("password");

        assertNotNull(result);
        assertFalse(result.isBlank());
    }

    @Test
    void shouldReturnSameHashForSamePassword() throws CredentialsException {
        String firstHash = CredentialsHelper.hashPassword("password");
        String secondHash = CredentialsHelper.hashPassword("password");

        assertEquals(firstHash, secondHash);
    }
}
