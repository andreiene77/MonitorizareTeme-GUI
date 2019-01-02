package AplicatieTeme.Utils;

public class Penalizare {
    private final Float nota;
    private final Integer deadline;
    private final Integer saptPredat;

    public Penalizare(Float nota, Integer deadline, Integer saptPredat) {
        this.nota = nota;
        this.deadline = deadline;
        this.saptPredat = saptPredat;
    }

    public Float calculateNotaMax() {
        if (saptPredat - deadline > 2)
            return (float) 0;
        if (saptPredat - deadline > 0)
            return (float) (10 - 2.5 * (saptPredat - deadline));
        return (float) 10;
    }

    public Float calculateNota() {
        if (saptPredat - deadline > 2)
            return (float) 0;
        if (saptPredat - deadline > 0)
            return (float) (nota - 2.5 * (saptPredat - deadline));
        return nota;
    }

    public Float calculatePenalizare() {
        if (saptPredat - deadline > 2)
            return (float) 10;
        if (saptPredat - deadline > 0)
            return (float) (2.5 * (saptPredat - deadline));
        return (float) 0;
    }
}
