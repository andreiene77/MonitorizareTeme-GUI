package AplicatieTeme.Domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class TemaTest {
    private final Tema t = new Tema("01", "ceva", 5, 3);

    @Test
    void getDescriere() {
        assertEquals(t.getDescriere(), "ceva");
    }

    @Test
    void setDescriere() {
        t.setDescriere("altceva");
        assertEquals(t.getDescriere(), "altceva");
    }

    @Test
    void getDeadlineWeek() {
        assertEquals(5, (int) t.getDeadlineWeek());
    }

    @Test
    void setDeadlineWeek() {
        t.setDeadlineWeek(6);
        assertEquals((int) t.getDeadlineWeek(), 6);
    }

    @Test
    void getStartWeek() {
        assertEquals(3, (int) t.getStartWeek());
    }

    @Test
    void setStartWeek() {
        t.setStartWeek(4);
        assertEquals(4, (int) t.getStartWeek());
    }

    @Test
    void prelungireDeadline() {
        Integer expected = t.getDeadlineWeek();
        t.prelungireDeadline(1);
        assertEquals(expected + 1, (int) t.getDeadlineWeek());
    }

    @Test
    void getID() {
        assertSame("01", t.getID());
    }

    @Test
    void setID() {
        t.setID("02");
        assertSame("02", t.getID());
    }
}