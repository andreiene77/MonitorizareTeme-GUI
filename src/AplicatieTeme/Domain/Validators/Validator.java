package AplicatieTeme.Domain.Validators;

/**
 * @param <E> - Interface of all types of entities validators
 */
public interface Validator<E> {
    /**
     * @param entity - entity to validate
     * @throws ValidationException - if validation failed
     */
    void validate(E entity);
}