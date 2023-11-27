package br.com.ist.job.openings.service;

import br.com.ist.job.annotation.HasUniqueConstraint;
import br.com.ist.job.openings.model.SuperEntity;
import br.com.ist.job.openings.repository.SuperRepo;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author jonny
 * @param <T>
 */
@Service
@Transactional
public abstract class SuperService<T extends SuperEntity> implements Serializable {

    public abstract SuperRepo<T> getRepo();

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Validator validator;

    public T save(T superEntity) {
        Set<ConstraintViolation<T>> violations = validator.validate(superEntity);

        if (!CollectionUtils.isEmpty(violations)) {
            Set<ConstraintViolation<T>> newViolations = new HashSet<>();
            for (ConstraintViolation violation : violations) {
                if (Objects.equals(violation.getConstraintDescriptor().getAnnotation().annotationType(), HasUniqueConstraint.class)) {
                    superEntity.getValidatorErrorMessages().stream().forEach(errorMessage -> {
                        newViolations.add(ConstraintViolationImpl.forReturnValueValidation(errorMessage, null, null,
                                messageSource.getMessage(errorMessage, null, Locale.getDefault()),
                                null, superEntity, errorMessage, superEntity, null, null, null, validator));
                    });
                    violations.remove(violation);
                    break;
                }
            }
            newViolations.addAll(violations);
            throw new ConstraintViolationException(newViolations);
        }

        return getRepo().save(superEntity);
    }

    public Page<T> list(T tExample, Pageable pageable) {
        if (Objects.nonNull(tExample)) {
            Example example = Example.of(tExample,
                    ExampleMatcher.matching()
                            .withIgnoreCase()
                            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                            .withIgnorePaths("creationDate", "active"));
            return getRepo().findAll(example, pageable);
        }
        return getRepo().findAll(pageable);
    }
    
    public void delete(T entity){
        getRepo().delete(entity);
    }
    
    public void delete(UUID id){
        getRepo().deleteById(id);
    }
}
