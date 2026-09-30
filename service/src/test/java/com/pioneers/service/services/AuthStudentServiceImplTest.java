package com.pioneers.service.services;

import com.pioneers.service.errors.exceptions.LoginException;
import com.pioneers.service.errors.exceptions.LogoutException;
import com.pioneers.service.errors.exceptions.RegisterException;
import com.pioneers.service.models.dtos.requests.StudentLogin;
import com.pioneers.service.models.dtos.requests.StudentRegister;
import com.pioneers.service.models.dtos.responses.GenericResponse;
import com.pioneers.service.models.dtos.responses.StudentResponse;
import com.pioneers.service.models.entities.Student;
import com.pioneers.service.repositories.StudentRepository;
import com.pioneers.service.services.students.AuthStudentServiceImpl;
import com.pioneers.service.utils.CredentialsHelper;
import com.pioneers.service.utils.validators.ValidationRulesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.pioneers.service.utils.CredentialsHelper.hashPassword;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthStudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private AuthStudentServiceImpl authStudentService;

    @Test
    void testSignup_whenRequestIsValidAndEmailDoesNotExist() {
        final String email = "omarkholeif@gmail.com";
        final String password = "Ok123456789!";
        final StudentRegister StudentRegisterRequest = new StudentRegister("Omar", "Kholeif", 20, email, password);

        when(studentRepository.findByEmail(email)).thenReturn(Optional.empty());

        authStudentService.signup(StudentRegisterRequest);

        final ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentCaptor.capture());
        final Student savedStudent = studentCaptor.getValue();

        assertEquals(email, savedStudent.getEmail());
        assertEquals(CredentialsHelper.hashPassword(password), savedStudent.getPassword());
        verify(studentRepository).findByEmail(email);
    }

    @Test
    void testSignup_whenRequestIsValidAndEmailAlreadyExists() {
        final String email = "omarkholeif@gmail.com";
        final String password = "Ok123456789!";
        final StudentRegister StudentRegisterRequest = new StudentRegister("Omar", "Kholeif", 20, email, password);

        final Student oldExistingStudent = Student.builder().email(email).build();
        when(studentRepository.findByEmail(email)).thenReturn(Optional.of(oldExistingStudent));

        assertThrows(RegisterException.class, () -> authStudentService.signup(StudentRegisterRequest));

        verify(studentRepository).findByEmail(email);
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void testSignup_whenRequestIsInvalid() {
        final String email = "omarkholeif@gmail.com";
        final String password = "weekpassword";
        final StudentRegister StudentRegisterRequest = new StudentRegister("Omar", "Kholeif", 20, email, password);

        final ValidationRulesService.ValidationException exception = assertThrows(
                ValidationRulesService.ValidationException.class, () -> authStudentService.signup(StudentRegisterRequest));

        assertTrue(exception.getErrors().containsKey("password"));
        verify(studentRepository, never()).findByEmail(any());
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void testLogin_whenRequestIsInvalid() {
        final String email = "omarkholeif@gmail.com";
        final String password = "weekpassword";
        final StudentLogin studentLoginRequest = new StudentLogin(email, password);

        assertThrows(ValidationRulesService.RuleException.class, ()-> authStudentService.login(studentLoginRequest) );
        verify(studentRepository, never()).findByEmail(email);

    }

    @Test
    void testLogin_whenRequestIsvalidAndEmailDoesNotExist() {
        final String email = "omarkholeif@gmail.com";
        final String password = "Ok123456789!";
        final StudentLogin studentLoginRequest = new StudentLogin(email, password);

        when(studentRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(LoginException.class, ()-> authStudentService.login(studentLoginRequest) );
        verify(studentRepository).findByEmail(email);

    }

    @Test
    void testLogin_whenPasswordDoesNotMatch() {
        final String email = "omarkholeif@gmail.com";
        final String password = "Ok123456789!";
        final StudentLogin studentLoginRequest = new StudentLogin(email, password);

        final Student oldExistingStudent = Student.builder().email(email).password("differentPassword").build();
        when(studentRepository.findByEmail(email)).thenReturn(Optional.of(oldExistingStudent));


        final LoginException exception = assertThrows(LoginException.class, ()-> authStudentService.login(studentLoginRequest) );
        verify(studentRepository).findByEmail(email);
        assertTrue(exception.getMessage().equals("Passwords don't match"));

    }

    @Test
    void testLogin_whenPasswordMatches() {
        final String email = "omarkholeif@gmail.com";
        final String password = "Ok123456789!";
        final StudentLogin studentLoginRequest = new StudentLogin(email, password);

        final String hashedPassword = hashPassword(password);

        final Student oldExistingStudent = Student.builder().email(email).password(hashedPassword).build();
        when(studentRepository.findByEmail(email)).thenReturn(Optional.of(oldExistingStudent));

        assertDoesNotThrow(()-> authStudentService.login(studentLoginRequest));
        verify(studentRepository).findByEmail(email);
        assertTrue(oldExistingStudent.isLoggedIn());


    }

    @Test
    void testLogout_whenEmailDoesNotExist() {
        final String email = "omarkholeif@gmail.com";
        when(studentRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(LogoutException.class, () -> authStudentService.logout(email));
        verify(studentRepository).findByEmail(email);
    }

    @Test
    void testLogout_whenEmailExistsAndNotLoggedIn() {
        final String email = "omarkholeif@gmail.com";
        final Student oldExistingStudent = Student.builder().email(email).build();

        when(studentRepository.findByEmail(email)).thenReturn(Optional.of(oldExistingStudent));

        assertThrows(LogoutException.class, () -> authStudentService.logout(email));
        verify(studentRepository).findByEmail(email);
    }

    @Test
    void testLogout_whenEmailExistsAndIsLoggedIn() {
        final String email = "omarkholeif@gmail.com";
        final Student oldExistingStudent = Student.builder().email(email).build();
        oldExistingStudent.login();

        when(studentRepository.findByEmail(email)).thenReturn(Optional.of(oldExistingStudent));

        assertDoesNotThrow(() -> authStudentService.logout(email));
        verify(studentRepository).findByEmail(email);
        assertTrue(!oldExistingStudent.isLoggedIn());
    }

    @Test
    void testSaveAll_whenAllEmailsAreNew() {
        final String email = "omarkholeif@gmail.com";
        final String password = "Ok123456789!";
        final StudentRegister request = new StudentRegister("Omar", "Kholeif", 21, email, password);

        when(studentRepository.findByEmail(email)).thenReturn(Optional.empty());

        final GenericResponse<?> response = authStudentService.saveAll(List.of(request));

        assertEquals("Successfully all registeredStudents successfully!", response.message());
        assertEquals(Optional.empty(), response.body());

        final ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentCaptor.capture());
        final Student savedStudent = studentCaptor.getValue();

        assertEquals(email, savedStudent.getEmail());
        assertEquals("Omar Kholeif", savedStudent.getFullName());
        assertEquals(CredentialsHelper.hashPassword(password), savedStudent.getPassword());

        verify(studentRepository, times(2)).findByEmail(email);
    }

    @Test
    void testSaveAll_whenAnEmailAlreadyExists() {
        final String email = "omarkholeif@gmail.com";
        final String password = "Ok123456789!";
        final StudentRegister request = new StudentRegister("Omar", "Kholeif", 21, email, password);

        final Student existingStudent =
                Student.builder().email(email).fullName("Omar Kholeif").age(21).password("hashedPassword").build();
        when(studentRepository.findByEmail(email)).thenReturn(Optional.of(existingStudent));

        final GenericResponse<?> response = authStudentService.saveAll(List.of(request));

        assertEquals("Those list are rejected to be inserted", response.message());
        assertEquals(List.of(new StudentResponse("Omar Kholeif", 21, email)), response.body());

        verify(studentRepository).findByEmail(email);
        verify(studentRepository, never()).save(any(Student.class));
    }
}
