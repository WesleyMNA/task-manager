package com.manager.taskapi.validators.notduplicated;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotDuplicatedValidator.class)
public @interface NotDuplicated {

    Class<?>[] groups() default {};

    String message() default "Value already exists";

    Class<? extends Payload>[] payload() default {};

    String jpql();
}
