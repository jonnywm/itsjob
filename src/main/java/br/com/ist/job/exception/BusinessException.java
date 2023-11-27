package br.com.ist.job.exception;

/**
 *
 * @author jonny
 */
public class BusinessException extends Exception{

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }
}
