package AplicatieTeme.Domain;

/**
 * The interface of any object with an id
 *
 * @param <ID> - the type of data that id is represented with
 */
public interface HasID<ID> {
    /**
     * @return - id of the object
     */
    ID getID();

    /**
     * @param id - update the id of the object
     */
    void setID(ID id);
}
