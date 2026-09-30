package com.pioneers.service.services;

import com.pioneers.service.errors.exceptions.StudentException;
import com.pioneers.service.models.dtos.responses.StudentResponse;
import com.pioneers.service.models.entities.Student;
import com.pioneers.service.repositories.StudentRepository;
import com.pioneers.service.services.students.StudentServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void testFindAllSortedByAge_whenStudentsExist() {
        final Student youngerStudent = student(
                UUID.randomUUID(), "Omar Kholeif", 20, "omarKholeif@gmail.com", "hashedpassword1", 99);
        final Student olderStudent = student(
                UUID.randomUUID(), "mahmoud kholeif", 21, "mahmoudkholeif@gmail.com", "hashedpassword2", 98);

        when(studentRepository.findAllSortedByAge()).thenReturn(List.of(youngerStudent, olderStudent));

        final List<StudentResponse> responses = studentService.findAllSortedByAge();

        assertEquals(List.of(
                new StudentResponse("Omar Kholeif", 20, "omarKholeif@gmail.com"),
                new StudentResponse("mahmoud kholeif", 21, "mahmoudkholeif@gmail.com")
        ), responses);

        verify(studentRepository).findAllSortedByAge();
    }

    @Test
    void testFindAllSortedByAge_whenNoStudentExists() {
        when(studentRepository.findAllSortedByAge()).thenReturn(List.of());

        final StudentException exception = assertThrows(StudentException.class, () -> studentService.findAllSortedByAge());

        assertEquals("No students found in the DB", exception.getMessage());
        verify(studentRepository).findAllSortedByAge();
    }

    @Test
    void testFindById_whenStudentExists() {
        final UUID id = UUID.randomUUID();
        final Student existingStudent = student(id, "Omar Kholeif", 20, "omarkholeif@gmail.com", "Ok123456789!", 99);
        when(studentRepository.findById(id)).thenReturn(Optional.of(existingStudent));

        final StudentResponse response = studentService.findById(id);

        assertEquals(new StudentResponse("Omar Kholeif", 20, "omarkholeif@gmail.com"), response);
        verify(studentRepository).findById(id);
    }

    @Test
    void testFindById_whenStudentDoesNotExist() {
        final UUID id = UUID.randomUUID();
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        final StudentException exception = assertThrows(StudentException.class, () -> studentService.findById(id));

        assertEquals("Student doesn't exist in the DB", exception.getMessage());
        verify(studentRepository).findById(id);
    }

    @Test
    void testFindAllSucceeded_whenThereIsSuccessfulStudents() {
        final Student successfulStudent = student(
                UUID.randomUUID(),  "Omar Kholeif", 20, "omarkholeif@gmail.com", "Ok123456789!", 99);

        when(studentRepository.findAllSucceeded()).thenReturn(List.of(successfulStudent));

        final Collection<StudentResponse> responses = studentService.findAllSucceeded();

        assertEquals(List.of(new StudentResponse("Omar Kholeif", 20, "omarkholeif@gmail.com")), responses);
        verify(studentRepository).findAllSucceeded();
    }

    @Test
    void testFindAllSucceeded_whenNoSuccessfulStudentsExist() {
        when(studentRepository.findAllSucceeded()).thenReturn(List.of());

        final StudentException exception = assertThrows(StudentException.class, () -> studentService.findAllSucceeded());

        assertEquals("No success students found in the DB", exception.getMessage());
        verify(studentRepository).findAllSucceeded();
    }

    private static Student student(final UUID id, final String fullName, final int age,
                                   final String email, final String password, final float score
    ) {
        return Student.builder().id(id).fullName(fullName).age(age).email(email).password(password).score(score).build();
    }

}
