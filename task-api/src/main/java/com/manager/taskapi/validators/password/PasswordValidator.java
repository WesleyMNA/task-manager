package com.manager.taskapi.validators.password;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PasswordValidator implements ConstraintValidator<CheckPasswords, Object> {

    private String password;
    private String confirmPassword;

    public void initialize(CheckPasswords constraintAnnotation) {
        this.password = constraintAnnotation.password();
        this.confirmPassword = constraintAnnotation.confirmPassword();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        String senha = (String) new BeanWrapperImpl(value)
                .getPropertyValue(this.password);
        String confirmarSenha = (String) new BeanWrapperImpl(value)
                .getPropertyValue(this.confirmPassword);
        return Objects.equals(senha, confirmarSenha);
    }
}
