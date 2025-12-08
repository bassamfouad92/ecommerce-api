package com.bsm.ecommerce_api.ecommerce_api.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = LowercasedValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Lowercased {
    String message() default "Value must be lowercased";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
