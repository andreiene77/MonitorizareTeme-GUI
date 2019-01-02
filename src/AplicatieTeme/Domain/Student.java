package AplicatieTeme.Domain;

import java.util.Arrays;
import java.util.List;

/**
 * Student type entities that implements HasID with a String ID
 */
public class Student implements HasID<String> {
    private String idStudent;
    private String numele;
    private String grupa;
    private String email;
    private String prof;

    /**
     * Constructor
     *
     * @param idStudent - String, unique identifier
     * @param numele    - String, name of the student
     * @param grupa     - String, grupa of the student
     * @param email     - String, student's e-mail
     * @param prof      - String, the name of Student's lab professor
     */
    public Student(String idStudent, String numele, String grupa, String email, String prof) {
        this.idStudent = idStudent;
        this.numele = numele;
        this.grupa = grupa;
        this.email = email;
        this.prof = prof;
    }

    public Student(String line) {
        List<String> attr = Arrays.asList(line.split(", "));
        this.idStudent = attr.get(0);
        this.numele = attr.get(1);
        this.grupa = attr.get(2);
        this.email = attr.get(3);
        this.prof = attr.get(4);
    }

    @Override
    public String toString() {
        return idStudent +
                ", " + numele +
                ", " + grupa +
                ", " + email +
                ", " + prof;
    }

    /**
     * @return - String, name of Student entity
     */
    public String getNumele() {
        return numele;
    }

    /**
     * @param numele - String, update the name of Student entity
     */
    public void setNumele(String numele) {
        this.numele = numele;
    }

    /**
     * @return - String, grupa of Student entity
     */
    public String getGrupa() {
        return grupa;
    }

    /**
     * @param grupa - String, update grupa of Student entity
     */
    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    /**
     * @return - String, e-mail of Student entity
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email - String, update the e-mail of Student entity
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return - String, lab professor of Student entity
     */
    public String getProf() {
        return prof;
    }

    /**
     * @param prof - String, update the lab professor of Student entity
     */
    public void setProf(String prof) {
        this.prof = prof;
    }

    /**
     * @return - String, id of Student entity
     */
    @Override
    public String getID() {
        return this.idStudent;
    }

    /**
     * @param id - String, update the id of the object
     */
    @Override
    public void setID(String id) {
        this.idStudent = id;
    }
}
