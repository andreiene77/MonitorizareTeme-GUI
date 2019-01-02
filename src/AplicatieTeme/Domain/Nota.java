package AplicatieTeme.Domain;

import AplicatieTeme.Utils.NotaID;

import java.util.Arrays;
import java.util.List;

public class Nota implements HasID<NotaID<String, String>> {
    private final NotaID<String, String> id;
    private final Integer data;
    private Float valoare;
    private String feedback;

    public Nota(String SID, String TID, Integer data, Float valoare, String feedback) {
        this.id = new NotaID<>(SID, TID);
        this.valoare = valoare;
        this.data = data;
        this.feedback = feedback;
    }

    public Nota(String line) {
        List<String> attr = Arrays.asList(line.split(", "));
        this.id = new NotaID<>(attr.get(0).replace("(", ""), attr.get(1).replace(")", ""));
        this.valoare = Float.parseFloat(attr.get(2));
        this.data = Integer.parseInt(attr.get(3));
        this.feedback = attr.get(4);
    }

    @Override
    public String toString() {
        return id +
                ", " + valoare +
                ", " + data +
                ", " + feedback;
    }

    public Float getValoare() {
        return valoare;
    }

    public void setValoare(Float valoare) {
        this.valoare = valoare;
    }

    public String getData() {
        return String.valueOf(data);
    }

    @Override
    public NotaID<String, String> getID() {
        return id;
    }

    @Override
    public void setID(NotaID<String, String> stringStringNotaID) {
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
