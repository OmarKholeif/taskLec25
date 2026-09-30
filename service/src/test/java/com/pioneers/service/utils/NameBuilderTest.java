package com.pioneers.service.utils;

import org.junit.jupiter.api.Test;

import static com.pioneers.service.utils.NameBuilder.buildFullName;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NameBuilderTest {
    @Test
    void TestbuildFullName() {
        final String firstName = "First";
        final String lastName = "Last";

        final String fullName = buildFullName(firstName, lastName);

        assertEquals(firstName + " " + lastName, fullName);
    }
}
