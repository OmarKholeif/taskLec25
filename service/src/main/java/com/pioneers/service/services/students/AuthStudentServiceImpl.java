package com.pioneers.service.services.students;

import com.pioneers.service.errors.exceptions.*;
import com.pioneers.service.models.dtos.requests.StudentLogin;
import com.pioneers.service.models.dtos.requests.StudentRegister;
import com.pioneers.service.models.dtos.responses.GenericResponse;
import com.pioneers.service.models.dtos.responses.StudentResponse;
import com.pioneers.service.models.entities.Student;
import com.pioneers.service.repositories.StudentRepository;

import com.pioneers.service.utils.mappers.StudentMapper;
import com.pioneers.service.utils.validators.ValidationRulesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pioneers.service.utils.CredentialsHelper.hashPassword;
import static com.pioneers.service.utils.NameBuilder.buildFullName;
import static com.pioneers.service.utils.builders.StudentBuilder.buildRegisteredStudent;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class AuthStudentServiceImpl implements AuthStudentService {

    private final StudentRepository studentRepositoryImpl;

    @Override
    public void signup(final StudentRegister studentRegisterRequest)
            throws RegisterException, ValidationRulesService.ValidationException {
        final String methodName = "signup";

        studentRegisterRequest.validate();

        final Optional<Student> optionalStudent = studentRepositoryImpl.findByEmail(studentRegisterRequest.email());

        if (optionalStudent.isPresent()) {
            final String logMessage =
                    String.format("%s, Errors in registration for email [%s], due to account is already registered",
                            methodName, studentRegisterRequest.email());

            throw new RegisterException(
                    String.format("Cannot find email [%s]", studentRegisterRequest.email()), logMessage);
        }

        final String fullName =
                buildFullName(studentRegisterRequest.firstName(), studentRegisterRequest.secondName());

        final String hashedPassword;
        try {
            hashedPassword = hashPassword(studentRegisterRequest.password());
        } catch (final CredentialsException e) {
            final String logMessage = String.format("%s, Cannot hash the [%s] user password due to: [%s]",
                    methodName, studentRegisterRequest.email(), e.getMessage());

            throw new RegisterException(e.getMessage(), logMessage);
        }

        final Student student = buildRegisteredStudent(
                studentRegisterRequest.age(),
                studentRegisterRequest.email(),
                fullName,
                hashedPassword
        );

        studentRepositoryImpl.save(student);

        log.debug("{}, Successfully registered student with email [{}]", methodName, student.getEmail());
    }

    @Override
    public void login(final StudentLogin studentLoginRequest) throws LoginException, CredentialsException {

        studentLoginRequest.validate();

        final Student foundStudent = studentRepositoryImpl.findByEmail(studentLoginRequest.email())
                .orElseThrow(() ->
                        new LoginException("Student with email: " + studentLoginRequest.email() + " not registered"));

        if (foundStudent.isLoggedIn()) {
            throw new LoginException("Student already logged in");
        }

        final String hashedPassword = hashPassword(studentLoginRequest.password());

        if (!hashedPassword.equals(foundStudent.getPassword())) {
            throw new LoginException("Passwords don't match");
        }

        foundStudent.login();
    }

    @Override
    public void logout(final String email) throws LogoutException {
        final Student foundStudent = studentRepositoryImpl.findByEmail(email)
                .orElseThrow(() -> new LogoutException("Student with email: " + email + " not registered"));

        foundStudent.logout();
    }

    @Override
    public GenericResponse<?> saveAll(final List<StudentRegister> studentRegisterRequests) throws RegisterException {

        final List<Student> registeredStudents = new ArrayList<>();

        studentRegisterRequests.forEach(request -> {
            Optional<Student> optionalStudent = studentRepositoryImpl.findByEmail(request.email());

            if (optionalStudent.isPresent()) {
                registeredStudents.add(optionalStudent.get());
                return;
            }
            signup(request);
        });

        if (registeredStudents.isEmpty()) {
            return new GenericResponse<>("Successfully all registeredStudents successfully!", Optional.empty());
        }

        final List<StudentResponse> rejectedStudentsList = registeredStudents.stream()
                .map(StudentMapper::toStudentResponse)
                .toList();

        return new GenericResponse<>("Those list are rejected to be inserted", rejectedStudentsList);
    }
}
