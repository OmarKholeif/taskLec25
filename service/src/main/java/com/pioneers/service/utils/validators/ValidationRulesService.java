package com.pioneers.service.utils.validators;

import com.pioneers.service.utils.times.TimeHelper;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import static com.pioneers.service.utils.StringUtils.isNullOrBlank;

public final class ValidationRulesService {

    private static final Map<ValidationRules, ValidatorService<String>> RULES = new EnumMap<>(ValidationRules.class);

    static {
        RULES.putIfAbsent(ValidationRules.FIRST_NAME, ValidationRulesService::validateFirstName);
        RULES.putIfAbsent(ValidationRules.SECOND_NAME, ValidationRulesService::validateSecondName);
        RULES.putIfAbsent(ValidationRules.AGE, ValidationRulesService::validateAge);
        RULES.putIfAbsent(ValidationRules.EMAIL, ValidationRulesService::validateEmail);
        RULES.putIfAbsent(ValidationRules.PASSWORD, ValidationRulesService::validatePassword);
        RULES.putIfAbsent(ValidationRules.SCORE, ValidationRulesService::validateScore);
    }

    private ValidationRulesService() {
        throw new AssertionError("Utility class");
    }

    public static void validate(final ValidationRules rule, final String ruleValue) throws RuleException {

        if (isNullOrBlank(ruleValue)) {
            throw new RuleException(rule.name(), "Rule is null or Blank");
        }

        final ValidatorService<String> validatorService = findRule(rule)
                .orElseThrow(() -> new RuleException(rule.name(), "Rule doesn't exist in our system"));

        validatorService.validate(ruleValue);
    }

    private static Optional<ValidatorService<String>> findRule(final ValidationRules rule) {
        return Optional.ofNullable(RULES.get(rule));
    }

    private static void validateFirstName(final String firstName) throws RuleException {
        if (firstName.length() < 2 || firstName.length() > 15) {
            throw new RuleException("firstName", "First Name must be between 2 and 15");
        }
    }

    private static void validateSecondName(final String secondName) throws RuleException {
        if (secondName.length() < 2 || secondName.length() > 15) {
            throw new RuleException("secondName", "Second Name must be between 2 and 15");
        }
    }

    private static void validateAge(final String age) throws RuleException {
        final int intAge = Integer.parseInt(age);
        if (intAge < 18 || intAge > 25) {
            throw new RuleException("age", "Age is incompatible");
        }
    }

    private static void validateEmail(final String email) throws RuleException {
        if (!email.contains("@") || !email.contains(".")) {
            throw new RuleException("email", "Email must be valid");
        }
    }

    private static void validatePassword(final String password) throws RuleException {
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9\\s])\\S{9,31}$")) {
            throw new RuleException("password", "Password doesn't meet the criteria");
        }
    }

    private static void validateScore(final String score) throws RuleException {
        final float floatScore = Float.parseFloat(score);
        if (floatScore < 50 || floatScore > 100) {
            throw new RuleException("score", "Score is not valid");
        }
    }

    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class RuleException extends RuntimeException {

        private final String message;
        private final String description;

        public RuleException(final String message, final String description) {
            super(description);

            this.message = message;
            this.description = description;
        }
    }

    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class ValidationException extends RuntimeException {

        private final Map<String, String> errors;

        private final Timestamp currentTimestamp = TimeHelper.currentTimestamp();

        public static final int CODE = 9001;

        public ValidationException(Map<String, String> errors) {
            this.errors = errors;
        }
    }
}
