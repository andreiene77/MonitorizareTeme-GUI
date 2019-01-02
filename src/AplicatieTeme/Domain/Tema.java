package AplicatieTeme.Domain;

import java.util.Arrays;
import java.util.List;

/**
 * Tema type entities that implements HasID with a String ID
 */
public class Tema implements HasID<String> {
    private String idTema;
    private String descriere;
    private Integer deadlineWeek;
    private Integer startWeek;

    /**
     * Constructor
     *
     * @param idTema       - String, unique identifier
     * @param descriere    - String, text of the homework
     * @param deadlineWeek - Integer, week at witch it should be finished
     * @param startWeek    - Integer, week at witch it was given
     */
    public Tema(String idTema, String descriere, Integer deadlineWeek, Integer startWeek) {
        this.idTema = idTema;
        this.descriere = descriere;
        this.deadlineWeek = deadlineWeek;
        this.startWeek = startWeek;
    }

    public Tema(String line) {
        List<String> attr = Arrays.asList(line.split(", "));
        this.idTema = attr.get(0);
        this.descriere = attr.get(1);
        this.deadlineWeek = Integer.valueOf(attr.get(2));
        this.startWeek = Integer.valueOf(attr.get(3));
    }

    @Override
    public String toString() {
        return idTema +
                ", " + descriere +
                ", " + deadlineWeek +
                ", " + startWeek;
    }

    /**
     * @return - String, text of the homework
     */
    public String getDescriere() {
        return descriere;
    }

    /**
     * @param descriere - String, update the text of the homework
     */
    void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    /**
     * @return, Integer, the number of the deadline week
     */
    public Integer getDeadlineWeek() {
        return deadlineWeek;
    }

    /**
     * @param deadlineWeek, Integer, update the deadline week
     */
    public void setDeadlineWeek(Integer deadlineWeek) {
        this.deadlineWeek = deadlineWeek;
    }

    /**
     * @return, Integer, the number of the starting week
     */
    public Integer getStartWeek() {
        return startWeek;
    }

    /**
     * @param startWeek, Integer, update the starting week
     */
    public void setStartWeek(Integer startWeek) {
        this.startWeek = startWeek;
    }

    /**
     * @param nrSapt, Integer, special update on deadline (adding nrSapt to
     *                current deadline)
     */
    public void prelungireDeadline(Integer nrSapt) {
        setDeadlineWeek(this.deadlineWeek + nrSapt);
    }

    /**
     * @return - String, id of Student entity
     */
    @Override
    public String getID() {
        return this.idTema;
    }

    /**
     * @param s - String, update the id of the entity
     */
    @Override
    public void setID(String s) {
        this.idTema = s;
    }
}
