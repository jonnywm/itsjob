package br.com.ist.job.annotation;

import br.com.ist.job.annotation.validator.HasUniqueConstraintValidator;
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
@Target(ElementType.TYPE)
@Constraint(validatedBy = HasUniqueConstraintValidator.class)
public @interface HasUniqueConstraint {

    String message() default "{fields.unique}";
    
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
