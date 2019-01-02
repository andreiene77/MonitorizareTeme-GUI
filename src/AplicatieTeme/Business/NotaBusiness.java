package AplicatieTeme.Business;

import AplicatieTeme.Domain.Nota;
import AplicatieTeme.Domain.Student;
import AplicatieTeme.Domain.Tema;
import AplicatieTeme.Domain.Validators.NotaValidator;
import AplicatieTeme.Repository.XMLNoteRepository;
import AplicatieTeme.Utils.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotaBusiness implements Observable<NoteChangeEvent> {
    private final XMLNoteRepository repoNote;
    private final List<Observer<NoteChangeEvent>> observers = new ArrayList<>();
    private StudentBusiness studBuss;
    private TemeBusiness temaBuss;
    private Integer saptCurenta;

    public NotaBusiness(StudentBusiness studBuss, TemeBusiness temaBuss) {
        NotaValidator validNote = new NotaValidator();
        repoNote = new XMLNoteRepository("./data/Catalog.xml", validNote);
        this.studBuss = studBuss;
        this.temaBuss = temaBuss;
        this.saptCurenta = 1;
    }

    @Override
    public void addObserver(Observer<NoteChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<NoteChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(NoteChangeEvent t) {
        observers.forEach(x -> x.update(t));
    }


    public StudentBusiness getStudBuss() {
        return studBuss;
    }

    public TemeBusiness getTemaBuss() {
        return temaBuss;
    }

    public Integer getSaptCurenta() {
        return saptCurenta;
    }

    public void setSaptCurenta(String saptCurenta) {
        this.saptCurenta = Integer.parseInt(saptCurenta);
        notifyObservers(new NoteChangeEvent(ChangeEventType.UPDATE, null));
    }

    public Penalizare addNota(Nota nota) {
        Penalizare penalizare = new Penalizare(nota.getValoare(), temaBuss.findTem(nota.getID().getTemaID()).getDeadlineWeek(), saptCurenta);

        nota.setValoare(penalizare.calculateNota());
        repoNote.save(nota);
        notifyObservers(new NoteChangeEvent(ChangeEventType.ADD, nota));
        writeNotaToFile(nota);
        return penalizare;
    }

    public Penalizare getPenalizare(Nota nota) {
        return new Penalizare(nota.getValoare(), temaBuss.findTem(nota.getID().getTemaID()).getDeadlineWeek(), saptCurenta);
    }

    public Nota getStudentTemaNotes(String SID, String TID) {
        try {
            return repoNote.findOne(new NotaID<>(SID, TID));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public Iterable<Nota> getAll() {
        return repoNote.findAll();
    }

    private void writeNotaToFile(Nota nota) {
        Student stud = studBuss.findStud(nota.getID().getStudentID());
        Tema tem = temaBuss.findTem(nota.getID().getTemaID());
        String fileName = "./data/" + stud.getNumele() + ".txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            List<String> lines = Arrays.asList("Tema: " + tem.getID(),
                    "Nota: " + nota.getValoare(),
                    "Predata in saptamana: " + saptCurenta,
                    "Deadline: " + tem.getDeadlineWeek(),
                    "Feedback: " + nota.getFeedback());
            lines.forEach(line -> {
                try {
                    bw.write(line);
                    bw.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
