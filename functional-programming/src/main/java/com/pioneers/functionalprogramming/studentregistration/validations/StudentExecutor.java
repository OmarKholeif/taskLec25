package com.pioneers.functionalprogramming.studentregistration.validations;

import com.pioneers.functionalprogramming.studentregistration.models.dtos.request.StudentRegister;

@FunctionalInterface
public interface StudentExecutor {

    void execute(StudentRegister studentRegister);
}
