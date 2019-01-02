package AplicatieTeme.Domain.Validators;

import AplicatieTeme.Domain.Tema;

/**
 * Validator for Tema entity
 */
public class TemaValidator implements Validator<Tema> {
    /**
     * method to validate Tema entity
     *
     * @param entity - Tema entity to validate
     */
    @Override
    public void validate(Tema entity) throws ValidationException {
        if (!validID(entity.getID()))
            throw new ValidationException("Numarul temei este invalid!");
        if (!validSaptamana(entity.getDeadlineWeek()))
            throw new ValidationException("Deadline-ul temei invalid!");
        if (!validSaptamana(entity.getStartWeek()))
            throw new ValidationException("Saptamana primirii temei invalid!");
        if (!validDeadlinePrimire(entity.getDeadlineWeek(), entity.getStartWeek()))
            throw new ValidationException("Deadline-ul nu poate fi inainte de primirea temei sau in aceeasi saptamana cu aceasta!");
    }

    /**
     * startWeek needs to be before deadline
     *
     * @param deadlineWeek - Integer
     * @param startWeek    - Integer
     * @return boolena
     */
    private boolean validDeadlinePrimire(Integer deadlineWeek, Integer startWeek) {
        return deadlineWeek >= startWeek;
    }

    /**
     * week must be between 1 and 14
     *
     * @param week - Integer
     * @return boolean
     */
    private boolean validSaptamana(Integer week) {
        try {
            if (week < 1 || week > 14)
                return false;
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Verify if id is parsable to int
     *
     * @param id - String, unique identifier
     * @return boolean
     */
    private boolean validID(String id) {
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

