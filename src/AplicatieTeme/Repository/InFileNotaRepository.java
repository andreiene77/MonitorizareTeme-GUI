package AplicatieTeme.Repository;

import AplicatieTeme.Domain.Nota;
import AplicatieTeme.Domain.Validators.Validator;
import AplicatieTeme.Utils.NotaID;

public class InFileNotaRepository extends AbstractFileRepository<NotaID<String, String>, Nota> {

    public InFileNotaRepository(String fileName, Validator<Nota> validator) {
        super(fileName, validator);
    }

    @Override
    public Nota extractEntity(String line) {
        return new Nota(line);
    }
}
