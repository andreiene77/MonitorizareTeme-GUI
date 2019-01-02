package AplicatieTeme.Business;

import AplicatieTeme.Domain.Tema;
import AplicatieTeme.Domain.Validators.TemaValidator;
import AplicatieTeme.Domain.Validators.ValidationException;
import AplicatieTeme.Repository.XMLTemeRepository;
import AplicatieTeme.Utils.ChangeEventType;
import AplicatieTeme.Utils.Observable;
import AplicatieTeme.Utils.Observer;
import AplicatieTeme.Utils.TemaChangeEvent;

import java.util.ArrayList;
import java.util.List;

public class TemeBusiness implements Observable<TemaChangeEvent> {
    private final XMLTemeRepository repoTeme;
    private final List<Observer<TemaChangeEvent>> observers = new ArrayList<>();

    public TemeBusiness() {
        TemaValidator validTeme = new TemaValidator();
        repoTeme = new XMLTemeRepository("./data/Teme.xml", validTeme);
    }

    @Override
    public void addObserver(Observer<TemaChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<TemaChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(TemaChangeEvent t) {
        observers.forEach(x -> x.update(t));
    }

    public Tema addTem(Tema tem) {
        Tema t = repoTeme.save(tem);
        if (t == null) {
            notifyObservers(new TemaChangeEvent(ChangeEventType.ADD, t));
        }
        return t;
    }

    public Tema findTem(String idTem) {
        return repoTeme.findOne(idTem);
    }

    public void postpone(String idTema, String nrSapt, Integer saptCurenta) throws ValidationException {
        Tema tem = repoTeme.findOne(idTema);
        if (saptCurenta >= tem.getDeadlineWeek())
            throw new ValidationException("Deadline-ul nu mai poate fi prelungit");
        else {
            tem.prelungireDeadline(Integer.parseInt(nrSapt));
            repoTeme.update(tem);
            notifyObservers(new TemaChangeEvent(ChangeEventType.UPDATE, tem));
        }
    }

    public Iterable<Tema> getAllTeme() {
        return repoTeme.findAll();
    }
}
