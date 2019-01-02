package AplicatieTeme.Repository;

import AplicatieTeme.Domain.Student;
import AplicatieTeme.Domain.Validators.StudentValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class InMemoryRepositoryTest {
    private final InMemoryRepository<String, Student> repo;
    private final Student s1 = new Student("01", "Andrei", "223", "andrei@email.com", "Profa");
    private final Student s11 = new Student("01", "Andrey", "225", "andrey@email.com", "Profas");
    private final Student s2 = new Student("02", "Alex", "223", "alex@email.com", "Profa");
    private final Student s3 = new Student("03", "Alin", "224", "alin@email.com", "Prof");

    InMemoryRepositoryTest() {
        StudentValidator valid = new StudentValidator();
        repo = new InMemoryRepository<>(valid);
    }

    @Test
    void save() {
        repo.save(s1);
        assertEquals(repo.findAll().spliterator().getExactSizeIfKnown(), 1);
        assertEquals(repo.findOne("01"), s1);
        assertNull(repo.findOne("02"));

        repo.save(s2);
        assertEquals(repo.findAll().spliterator().getExactSizeIfKnown(), 2);
        assertEquals(repo.findOne("02"), s2);
        assertNull(repo.findOne("03"));
    }

    @Test
    void findOne() {
        assertNull(repo.findOne("03"));
        repo.save(s3);
        assertEquals(repo.findOne("03"), s3);
        repo.save(s2);
        repo.save(s1);
        assertEquals(repo.findOne("02").getNumele(), "Alex");
        assertEquals(repo.findOne("02").getGrupa(), "223");
        assertEquals(repo.findOne("02").getEmail(), "alex@email.com");
        assertEquals(repo.findOne("02").getProf(), "Profa");
        assertNull(repo.findOne("04"));
    }

    @Test
    void findAll() {
        assertEquals(repo.findAll().spliterator().getExactSizeIfKnown(), 0);
        repo.save(s1);
        assertEquals(repo.findAll().spliterator().getExactSizeIfKnown(), 1);
        repo.save(s2);
        assertEquals(repo.findAll().spliterator().getExactSizeIfKnown(), 2);
        repo.save(s3);
        assertEquals(repo.findAll().spliterator().getExactSizeIfKnown(), 3);
    }

    @Test
    void delete() {
        repo.save(s1);
        repo.save(s2);
        repo.save(s3);
        assertEquals(repo.delete("01"), s1);
        assertEquals(repo.findAll().spliterator().getExactSizeIfKnown(), 2);
        assertNull(repo.delete("01"));
        assertEquals(repo.findAll().spliterator().getExactSizeIfKnown(), 2);

        assertEquals(repo.delete("02"), s2);
        assertEquals(repo.findAll().spliterator().getExactSizeIfKnown(), 1);

        assertEquals(repo.delete("03"), s3);
        assertEquals(repo.findAll().spliterator().getExactSizeIfKnown(), 0);
    }

    @Test
    void update() {
        repo.save(s1);
        repo.update(s11);
        assertEquals(repo.findOne("01").getNumele(), "Andrey");
        assertEquals(repo.findOne("01").getGrupa(), "225");
        assertEquals(repo.findOne("01").getEmail(), "andrey@email.com");
        assertEquals(repo.findOne("01").getProf(), "Profas");
    }
}