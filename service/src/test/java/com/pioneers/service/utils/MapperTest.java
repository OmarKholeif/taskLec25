package com.pioneers.service.utils;

import com.pioneers.service.models.dtos.responses.StudentResponse;
import com.pioneers.service.models.entities.Student;
import com.pioneers.service.utils.mappers.StudentMapper;
import org.junit.jupiter.api.Test;

import static com.pioneers.service.utils.builders.StudentBuilder.buildRegisteredStudent;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapperTest {

    @Test
    public void testToStudentResponse() {
        final int age = 26;
        final String email = "omarkholeif@gmail.com";
        final String fullName = "omar kholeif";
        final String hashedPassword = "hashed-password";

        final Student student = buildRegisteredStudent(age, email, fullName, hashedPassword);
        final StudentResponse studentResponse = StudentMapper.toStudentResponse(student);

        assertAll(
                () -> assertEquals(age, studentResponse.age()),
                () -> assertEquals(email, studentResponse.email()),
                () -> assertEquals(fullName, studentResponse.fullName())
        );
    }
}
