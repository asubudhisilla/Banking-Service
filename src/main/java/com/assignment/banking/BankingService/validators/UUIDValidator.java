package com.assignment.banking.BankingService.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.UUID;

public class UUIDValidator implements ConstraintValidator<ValidUUID, UUID> {
    private final String regex = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}";// the regex

    @Override
    public void initialize(ValidUUID validUuid) { }

    @Override
    public boolean isValid(UUID uuid, ConstraintValidatorContext cxt) {
        if(uuid == null || (uuid !=null &&uuid.toString().isBlank()))
            return false;
        return uuid.toString().matches(this.regex);
    }
}
