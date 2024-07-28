package com.example.demo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class BeforeValidator implements ConstraintValidator<BeforeDate, LocalDateTime> {
    private LocalDateTime date;

    public void initialize() {
        date = LocalDateTime.now();
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;
        if (value != null) {
            if (!value.isBefore(LocalDateTime.now())) {

                String defaultErrorMessage = constraintValidatorContext.getDefaultConstraintMessageTemplate();
                String formattedErrorMessage = String.format(defaultErrorMessage, LocalDateTime.now());

                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(formattedErrorMessage).addConstraintViolation();
                valid = false;
            }
        }
        return valid;
    }
    }

