package com.pioneers.service.utils;

import com.pioneers.service.models.entities.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static com.pioneers.service.utils.builders.StudentBuilder.buildRegisteredStudent;

class StudentBuilderTest {

    @Test
    void TestBuildRegisteredStudent_assertStudentFieldsAndCreatesV4Id() {
        final int age = 26;
        final String email = "omarkholeif@gmail.com";
        final String fullName = "omar kholeif";
        final String hashedPassword = "hashed-password";

        final Student student = buildRegisteredStudent(age, email, fullName, hashedPassword);

        assertAll(
                () -> assertNotNull(student.getId()),
                () -> assertEquals(4, student.getId().version()),
                () -> assertEquals(age, student.getAge()),
                () -> assertEquals(email, student.getEmail()),
                () -> assertEquals(fullName, student.getFullName()),
                () -> assertEquals(hashedPassword, student.getPassword())
        );
    }

    @Test
    void testBuildRegisteredStudent_assertGenerationOfAnewIdForEachStudent() {

        final Student firstStudent = buildRegisteredStudent(21, "first@gmail.com", "First Student", "hashPassword1");
        final Student secondStudent = buildRegisteredStudent(22, "second@gmail.com", "Second Student", "hashPassword2");

        assertNotEquals(firstStudent.getId(), secondStudent.getId());
    }
}
