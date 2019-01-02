package AplicatieTeme.Business;

import AplicatieTeme.Domain.Student;
import AplicatieTeme.Domain.Validators.StudentValidator;
import AplicatieTeme.Repository.XMLStudentRepository;
import AplicatieTeme.Utils.ChangeEventType;
import AplicatieTeme.Utils.Observable;
import AplicatieTeme.Utils.Observer;
import AplicatieTeme.Utils.StudentChangeEvent;

import java.util.ArrayList;
import java.util.List;

public class StudentBusiness implements Observable<StudentChangeEvent> {
    private final XMLStudentRepository repoStud;
    private final List<Observer<StudentChangeEvent>> observers = new ArrayList<>();

    public StudentBusiness() {
        StudentValidator validStud = new StudentValidator();
        repoStud = new XMLStudentRepository("./data/Studenti.xml", validStud);
    }

    @Override
    public void addObserver(Observer<StudentChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<StudentChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(StudentChangeEvent t) {
        observers.forEach(x -> x.update(t));
    }

    public Student addStud(Student stud) {
        Student s = repoStud.save(stud);
        if (s == null) {
            notifyObservers(new StudentChangeEvent(ChangeEventType.ADD, s));
        }
        return s;
    }

    public Student updateStud(Student stud) {
        Student olds = repoStud.findOne(stud.getID());
        Student news = repoStud.update(stud);
        notifyObservers(new StudentChangeEvent(ChangeEventType.UPDATE, olds, news));
        return news;
    }

    public Student removeStud(String idStudent) {
        Student s = repoStud.delete(idStudent);
        if (s != null) {
            notifyObservers(new StudentChangeEvent(ChangeEventType.DELETE, s));
        }
        return s;
    }

    public Student findStud(String idStudent) {
        return repoStud.findOne(idStudent);
    }

    public Iterable<Student> getAll() {
        return repoStud.findAll();
    }

    public Student findStudName(String name) {
        for (Student e : getAll()) {
            if (e.getNumele().equals(name)) {
                return e;
            }
        }
        return null;
    }
}
