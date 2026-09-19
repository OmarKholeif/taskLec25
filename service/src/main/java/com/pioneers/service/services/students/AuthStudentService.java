package com.pioneers.service.services.students;

import com.pioneers.service.errors.exceptions.*;
import com.pioneers.service.models.dtos.requests.StudentLogin;
import com.pioneers.service.models.dtos.requests.StudentRegister;
import com.pioneers.service.models.dtos.responses.GenericResponse;
import jakarta.validation.constraints.Email;

import java.util.List;

public interface AuthStudentService {

    void signup(StudentRegister studentRegisterRequest) throws RegisterException;

    void login(StudentLogin studentLoginRequest) throws LoginException, CredentialsException;

    void logout(@Email(message = "{validation.email.pattern}") String email) throws LogoutException;

    GenericResponse<?> saveAll(final List<StudentRegister> studentRegisterRequests) throws RegisterException;
}
