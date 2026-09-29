package com.pioneers.functionalprogramming.studentregistration.validations;

import com.pioneers.functionalprogramming.studentregistration.models.dtos.request.StudentRegister;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

public class RegistrationValidator {

    private RegistrationValidator(){
        throw new AssertionError("utility class");
    }

    public static ValidationResult validate(StudentRegister studentRegister){
        try {

            isValidName(studentRegister.firstName());
            isValidName(studentRegister.lastName());
            isValidAge(studentRegister.age());

        } catch (ValidationException e) {
            return new ValidationResult(false,studentRegister);
        }

        return new ValidationResult(true,studentRegister);

    }

    private static void isValidName(String name) throws ValidationException {
        if (name == null || name.isBlank() || name.length() < 3 || name.length() > 20) {
            throw new ValidationException("Name must be between 3 and 20");
        }
    }

    private static void isValidAge(int age) throws ValidationException {
        if(age < 18 || age > 25){
            throw new ValidationException("Age is incompatible");
        }
    }

    @Slf4j
    @Getter
    @EqualsAndHashCode(callSuper = false)
    private static class ValidationException extends RuntimeException {

        public ValidationException(final String message) {
            super(message);
            log.error(message);
        }
    }
}
