package com.assignment.banking.BankingService.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = UUIDValidator.class)
public @interface ValidUUID {

    String message() default "{invalid.Account Number}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}