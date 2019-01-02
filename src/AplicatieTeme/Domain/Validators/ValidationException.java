package AplicatieTeme.Domain.Validators;

/**
 * Exception class for validation fails
 */
public class ValidationException extends RuntimeException {
    /**
     * Constructor
     *
     * @param msg - String, description of exception
     */
    public ValidationException(String msg) {
        super(msg);
    }

}
