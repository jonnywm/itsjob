package br.com.ist.job.annotation;

import br.com.ist.job.annotation.validator.NotEmptyListValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author jonny
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmptyListValidator.class)
public @interface NotEmptyList {

    String message() default "{list.required}";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
