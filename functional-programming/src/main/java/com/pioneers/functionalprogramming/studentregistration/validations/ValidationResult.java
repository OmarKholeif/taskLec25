package com.pioneers.functionalprogramming.studentregistration.validations;

import com.pioneers.functionalprogramming.studentregistration.models.dtos.request.StudentRegister;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidationResult {

    private final boolean valid;
    private final StudentRegister studentRegister;

    public void onSuccess(StudentExecutor executor){
        if(valid){
            executor.execute(studentRegister);
        }
    }

    public void onFailure(Runnable executor){
        if(!valid){
            executor.run();
        }
    }
}
