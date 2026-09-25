package com.pioneers.service.models.dtos.requests;

import com.pioneers.service.utils.validators.ValidationRules;
import com.pioneers.service.utils.validators.ValidationRulesService;

public record StudentUpdate(String firstName, String secondName, int age, String email, String password, float score) {

    public void validate() throws ValidationRulesService.ValidationException {
        ValidationRulesService.validate(ValidationRules.FIRST_NAME, firstName);
        ValidationRulesService.validate(ValidationRules.SECOND_NAME, secondName);
        ValidationRulesService.validate(ValidationRules.AGE, String.valueOf(age));
        ValidationRulesService.validate(ValidationRules.EMAIL, email);
        ValidationRulesService.validate(ValidationRules.PASSWORD, password);
        ValidationRulesService.validate(ValidationRules.SCORE, String.valueOf(score));
    }

    @Override
    public String toString() {
        return "StudentUpdate{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", score=" + score +
                ", password='***********" + '\'' +
                '}';
    }
}
