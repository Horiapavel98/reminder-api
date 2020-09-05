package com.horia.reminderapi.client;

import com.horia.reminderapi.annotations.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        UserDto userDto = (UserDto) object;
        return userDto.getPassword().equals(userDto.getMatchingPassword());
    }
}
