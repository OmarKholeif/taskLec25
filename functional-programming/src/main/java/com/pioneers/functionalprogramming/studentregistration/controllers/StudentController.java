package com.pioneers.functionalprogramming.studentregistration.controllers;

import com.pioneers.functionalprogramming.studentregistration.models.dtos.request.StudentRegister;
import com.pioneers.functionalprogramming.studentregistration.services.StudentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("register")
    public String registerStudentApi(@RequestBody final StudentRegister studentRegister) {
        final String methodName = "registerStudentApi";
        log.info("{}, Starting Registration flow for student with email [{}]", methodName, studentRegister.email());

        studentService.register(studentRegister);

        log.info("{}, Successfully registered student with email [{}]", methodName, studentRegister.email());
        return "Successfully registered student with email: " + studentRegister.email();
    }
}

