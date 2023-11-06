package com.manager.taskapi.validators.notduplicated;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class NotDuplicatedValidator implements ConstraintValidator<NotDuplicated, String> {

    private final EntityManager entityManager;
    private String jpql;

    @Override
    public void initialize(NotDuplicated constraintAnnotation) {
        this.jpql = constraintAnnotation.jpql();
        validateJPQL();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Query query = entityManager.createQuery(jpql);
        query.setParameter("value", value);
        Long count = (Long) query.getSingleResult();
        return count < 1;
    }

    private void validateJPQL() {
        if (!jpql.contains("= :value"))
            throw new IllegalArgumentException("JPQL must have the parameter ':value' for comparison");

        if (!jpql.contains("COUNT"))
            throw new IllegalArgumentException("JPQL must have COUNT function call");
    }
}
