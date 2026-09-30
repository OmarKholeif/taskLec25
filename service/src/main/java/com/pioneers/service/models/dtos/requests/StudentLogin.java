package com.pioneers.service.models.dtos.requests;

import com.pioneers.service.utils.validators.ValidationRules;
import com.pioneers.service.utils.validators.ValidationRulesService;

public record StudentLogin(
        String email,
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
