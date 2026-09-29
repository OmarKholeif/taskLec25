package com.pioneers.functionalprogramming.studentregistration.services;

import com.pioneers.functionalprogramming.studentregistration.errors.exceptions.CredentialsException;
import com.pioneers.functionalprogramming.studentregistration.errors.exceptions.RegisterException;
import com.pioneers.functionalprogramming.studentregistration.models.dtos.request.StudentRegister;
import com.pioneers.functionalprogramming.studentregistration.models.entities.Student;
import com.pioneers.functionalprogramming.studentregistration.repositories.StudentRepository;

import com.pioneers.functionalprogramming.studentregistration.utils.CredentialsHelper;
import com.pioneers.functionalprogramming.studentregistration.validations.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public void register(final StudentRegister studentRegister) throws RegisterException{
        final String methodName = "Register";

        RegistrationValidator.validate(studentRegister).onFailure(()-> {
            final String logMessege =
                    String.format("%s, Errors in registration due to Invalid Student Register", methodName);
            throw new RegisterException("Invalid Student Register",logMessege);
        });

        studentRepository.findByEmail(studentRegister.email())
                .ifPresent(student -> {
                    final String logMessege =
                            String.format("%s, Errors in registration for email [%s], due to account is already registered",
                            methodName, studentRegister.email());

                    throw new RegisterException("Student already exists",logMessege);
                });

        final String fullName = studentRegister.buildFullName();
        final String hashedPassword;
        try {
            hashedPassword = CredentialsHelper.hashPassword(studentRegister.password());
        } catch (CredentialsException e) {
            final String logMessege = String.format("%s, Errors in registration for email [%s], due to unable to hash password",
                            methodName, studentRegister.email());
            throw new RegisterException(e.getMessage(),logMessege);
        }

        final Student newStudent = new Student(fullName,studentRegister.age(),studentRegister.email(),hashedPassword);
        studentRepository.save(newStudent);

        log.debug("{}, Successfully registered student ", methodName);
    }
}
