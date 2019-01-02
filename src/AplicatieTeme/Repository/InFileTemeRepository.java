package AplicatieTeme.Repository;

import AplicatieTeme.Domain.Tema;
import AplicatieTeme.Domain.Validators.Validator;

public class InFileTemeRepository extends AbstractFileRepository<String, Tema> {
    public InFileTemeRepository(String fileName, Validator<Tema> validator) {
        super(fileName, validator);
    }

    @Override
    public Tema extractEntity(String line) {
        return new Tema(line);
    }
}
