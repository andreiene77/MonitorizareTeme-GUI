package AplicatieTeme.Repository;

import AplicatieTeme.Domain.Student;
import AplicatieTeme.Domain.Validators.Validator;

public class InFileStudentRepository extends AbstractFileRepository<String, Student> {
    public InFileStudentRepository(String fileName, Validator<Student> validator) {
        super(fileName, validator);
    }

    @Override
    public Student extractEntity(String line) {
        return new Student(line);
    }
}
