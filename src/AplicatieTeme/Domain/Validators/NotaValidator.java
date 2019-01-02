package AplicatieTeme.Domain.Validators;

import AplicatieTeme.Domain.Nota;
import AplicatieTeme.Utils.NotaID;

public class NotaValidator implements AplicatieTeme.Domain.Validators.Validator<Nota> {
    @Override
    public void validate(Nota entity) throws ValidationException {
        if (!validateId(entity.getID())) {
            throw new ValidationException("ID-ul este invalid!");
        }
        if (!validateValoare(entity.getValoare())) {
            throw new ValidationException("Nota trebuie sa fie intre 0 si 10!");
        }
    }

    private boolean validateId(NotaID<String, String> id) {
        try {
            Integer.parseInt(id.getStudentID());
            Integer.parseInt(id.getTemaID());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validateValoare(Float valoare) {
        return valoare <= 10 && valoare >= 0;
    }
}
