package com.pioneers.service.models.dtos.requests;

import com.pioneers.service.utils.validators.ValidationRules;
import com.pioneers.service.utils.validators.ValidationRulesService;

public record StudentLogin(
//        @Email(message = "Email must be valid")
        String email,
//        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9\\s])\\S{9,31}$",
//                message = "Password doesn't meed the criteria")
        String password) {

    public void validate() throws ValidationRulesService.RuleException {
        ValidationRulesService.validate(ValidationRules.EMAIL, email);
        ValidationRulesService.validate(ValidationRules.PASSWORD, password);
    }

    @Override
    public String toString() {
        return "StudentLogin{" +
                "email='" + email + '\'' +
                ", password='**********'" +
                '}';
    }
}
