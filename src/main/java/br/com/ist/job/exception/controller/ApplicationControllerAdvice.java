package br.com.ist.job.exception.controller;

import br.com.ist.job.exception.BusinessException;
import br.com.ist.job.exception.dto.APIErrorsDTO;
import java.util.Locale;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 *
 * @author jonny
 */
@RestControllerAdvice
public class ApplicationControllerAdvice {
    
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIErrorsDTO handleBusinessException(BusinessException be) {
        return APIErrorsDTO.build(messageSource.getMessage(be.getMessage(), null, Locale.getDefault()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIErrorsDTO handleMethodNotValidException(MethodArgumentNotValidException manve) {
        return APIErrorsDTO.build(manve.getBindingResult().getAllErrors()
                .stream().map(error -> error.getDefaultMessage())
                .collect(Collectors.toList()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public APIErrorsDTO handleMethodDataIntegrityViolationException(DataIntegrityViolationException dive) {
        return APIErrorsDTO.build(dive.getMessage());
    }
    
    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public APIErrorsDTO handleMethodConstraintViolationException(jakarta.validation.ConstraintViolationException cve) {
        return APIErrorsDTO.build(cve.getConstraintViolations()
                .stream().map(constr -> constr.getMessage())
                .collect(Collectors.toList()));
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public APIErrorsDTO handleHttpMessageNotReadableException(HttpMessageNotReadableException hmnre) {
        return APIErrorsDTO.build(hmnre.getMessage());
    }
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public APIErrorsDTO handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException hmnre) {
        return APIErrorsDTO.build(hmnre.getMessage());
    }
}
