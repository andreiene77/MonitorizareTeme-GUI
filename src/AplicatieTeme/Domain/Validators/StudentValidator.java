package AplicatieTeme.Domain.Validators;

import AplicatieTeme.Domain.Student;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator for student entity
 */
public class StudentValidator implements AplicatieTeme.Domain.Validators.Validator<Student> {
    /**
     * Validation method
     *
     * @param entity - Student entity to validate
     * @throws ValidationException - if validation fails
     */
    @Override
    public void validate(Student entity) throws ValidationException {
        if (!validateID(entity.getID()))
            throw new ValidationException("ID-ul este invalid!");
        if (!validateNume(entity.getNumele()))
            throw new ValidationException("Numele studentului este invalid!");
        if (!validateNume(entity.getProf()))
            throw new ValidationException("Numele profului este invalid!");
        if (!validateGrupa(entity.getGrupa()))
            throw new ValidationException("Grupa este invalida!");
        if (!validateEmail(entity.getEmail()))
            throw new ValidationException("E-mail-ul este invalid!");
    }

    /**
     * Verify if id is parsable to int
     *
     * @param id - String, unique identifier
     * @return boolean
     */
    private boolean validateID(String id) {
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Verify if name is valid
     *
     * @param nume - String, name to verify
     * @return boolean
     */
    private boolean validateNume(String nume) {
        Pattern patternNume = Pattern.compile("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
        Matcher matcherNume = patternNume.matcher(nume);
        return matcherNume.matches();
    }

    /**
     * Verify if grupa is from second year at Info UBB
     *
     * @param grupa - String, grupa to verify
     * @return boolean
     */
    private boolean validateGrupa(String grupa) {
        Pattern patternGrupa = Pattern.compile("(22[1-7])");
        Matcher matcherGrupa = patternGrupa.matcher(grupa);
        return matcherGrupa.matches();
    }

    /**
     * Verify if e-mail si a valid format
     *
     * @param email - String, e-mail to verify
     * @return boolean
     */
    private boolean validateEmail(String email) {
        Pattern patternEmail = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?)*$");
        Matcher matcherEmail = patternEmail.matcher(email);
        return matcherEmail.matches();
    }
}
