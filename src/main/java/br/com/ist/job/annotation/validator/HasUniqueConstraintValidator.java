package br.com.ist.job.annotation.validator;

import br.com.ist.job.annotation.HasUniqueConstraint;
import br.com.ist.job.annotation.Unique;
import br.com.ist.job.annotation.ValidatorMessageField;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author jonny
 */
@Component
public class HasUniqueConstraintValidator implements ConstraintValidator<HasUniqueConstraint, Object> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(HasUniqueConstraint huc) {
        ConstraintValidator.super.initialize(huc);
    }

    @Override
    public boolean isValid(Object objectToValidate, ConstraintValidatorContext cvc) {
        try {
            Class clazz = objectToValidate.getClass();
            Object instance = clazz.getDeclaredConstructor().newInstance();

            cleanFields(instance);

            List<Field> fields = getUniqueFields(clazz);

            if (!CollectionUtils.isEmpty(fields)) {

                Field validatorMessageField = getFieldByAnnotation(clazz, ValidatorMessageField.class);

                fields.forEach(field -> {
                    try {
                        field.set(instance, field.get(objectToValidate));
                        
                        Example example = Example.of(instance,
                                ExampleMatcher.matching()
                                        .withIgnorePaths("creationDate", "active")
                                        .withIgnoreCase());
                        
                        Optional<Object> optional = new SimpleJpaRepository<>(clazz, entityManager).findOne(example);
                        
                        if (optional.isPresent()) {
                            
                            Object objectDB = optional.get();
                            
                            if (!Objects.equals(clazz.cast(objectDB), clazz.cast(objectToValidate))) {
                                
                                try {
                                    if (Objects.isNull(validatorMessageField.get(objectToValidate))) {
                                        
                                        if (!Objects.equals(validatorMessageField.getType(), List.class)) {
                                            throw new RuntimeException("Field " + validatorMessageField.getName() + " must be a java.util.List");
                                        }
                                        validatorMessageField.set(objectToValidate, new ArrayList<>(fields.size()));
                                    }

                                    List<String> validatorMessages = (List<String>) validatorMessageField.get(objectToValidate);
                                    validatorMessages.add(field.getAnnotation(Unique.class).message());
                                    validatorMessageField.set(objectToValidate, validatorMessages);
                                    
                                } catch (IllegalArgumentException | IllegalAccessException ex) {
                                    Logger.getLogger(HasUniqueConstraintValidator.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        Logger.getLogger(HasUniqueConstraintValidator.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });
                return CollectionUtils.isEmpty(((List<String>) validatorMessageField.get(objectToValidate)));
            }
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException ex) {
            Logger.getLogger(HasUniqueConstraintValidator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    private void cleanFields(Object instance) {
        cleanFields(instance, null, null);
    }

    private void cleanFields(Object instance, String uniqueField, Object fieldToValidate) {
        getFields(instance.getClass()).forEach(field -> {
            field.setAccessible(true);
            try {
                if (Objects.equals(field.getName(), uniqueField)) {
                    field.set(instance, fieldToValidate);
                } else {
                    field.set(instance, null);
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(HasUniqueConstraintValidator.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private List<Field> getUniqueFields(Class clazz) {
        return getFields(clazz)
                .filter(field -> field.isAnnotationPresent(Unique.class))
                .map(field -> {
                    field.setAccessible(true);
                    return field;
                })
                .collect(Collectors.toList());
    }

    private Field getFieldByAnnotation(Class clazz, Class<? extends Annotation> annotation) {
        return getFields(clazz)
                .filter(field -> field.isAnnotationPresent(annotation))
                .map(field -> {
                    field.setAccessible(true);
                    return field;
                })
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Annotation " + annotation.getName() + " is not present on " + clazz.getName() + " or its parents."));
    }

    private Stream<Field> getFields(Class clazz) {
        return Stream.concat(Arrays.stream(clazz.getDeclaredFields()),
                Arrays.stream(clazz.getSuperclass().getDeclaredFields()))
                .filter(field -> (field.getModifiers() & Modifier.FINAL) != Modifier.FINAL);
    }
}
