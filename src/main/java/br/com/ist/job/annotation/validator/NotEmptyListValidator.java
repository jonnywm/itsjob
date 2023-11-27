package br.com.ist.job.annotation.validator;

import br.com.ist.job.annotation.NotEmptyList;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author jonny
 */
public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List>{

    @Override
    public void initialize(NotEmptyList constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List list, ConstraintValidatorContext cvc) {
        return !CollectionUtils.isEmpty(list);
    }

}
