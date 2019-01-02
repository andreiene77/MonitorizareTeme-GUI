package AplicatieTeme.Domain.Validators;

import AplicatieTeme.Utils.Strategy;

public class ValidatorFactory {
    private static final ValidatorFactory instance = new ValidatorFactory();

    private ValidatorFactory() {
    }

    public static ValidatorFactory getInstance() {
        return instance;
    }

    public Validator createValidator(Strategy s) {
        if (s == Strategy.Tema)
            return new TemaValidator();
        if (s == Strategy.Student)
            return new StudentValidator();
        return null;
    }
}

