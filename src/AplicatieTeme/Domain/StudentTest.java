package AplicatieTeme.Domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class StudentTest {
    private final Student st = new Student("01", "Andrei", "223", "andrei@email.com", "Profa");

    @Test
    void getNumele() {
        assertSame("Andrei", st.getNumele());
    }

    @Test
    void setNumele() {
        st.setNumele("Andrew");
        assertSame("Andrew", st.getNumele());
    }

    @Test
    void getGrupa() {
        assertSame("223", st.getGrupa());
    }

    @Test
    void setGrupa() {
        st.setGrupa("233");
        assertSame("233", st.getGrupa());
    }

    @Test
    void getEmail() {
        assertSame("andrei@email.com", st.getEmail());
    }

    @Test
    void setEmail() {
        st.setEmail("andrew@email.com");
        assertSame("andrew@email.com", st.getEmail());
    }

    @Test
    void getProf() {
        assertSame("Profa", st.getProf());
    }

    @Test
    void setProf() {
        st.setProf("Profesoara");
        assertSame("Profesoara", st.getProf());
    }

    @Test
    void getID() {
        assertSame("01", st.getID());
    }

    @Test
    void setID() {
        st.setID("02");
        assertSame("02", st.getID());
    }
}