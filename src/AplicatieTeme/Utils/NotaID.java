package AplicatieTeme.Utils;

import java.util.Objects;

public class NotaID<TIDS, TIDT> {
    private TIDS studentID;
    private TIDT temaID;

    public NotaID(TIDS studentID, TIDT temaID) {
        this.studentID = studentID;
        this.temaID = temaID;
    }

//    public boolean equals (Object other) {
//        if (other instanceof NotaID) {
//            NotaID otherNotaID = (NotaID) other;
//            return ((this.studentID == otherNotaID.studentID ||
//                    (this.studentID != null &&
//                            otherNotaID.studentID != null &&
//                            this.studentID.equals(otherNotaID.studentID))) &&
//                    (this.temaID == otherNotaID.temaID ||
//                            (this.temaID != null &&
//                                    otherNotaID.temaID != null &&
//                                    this.temaID.equals(otherNotaID.temaID))));
//        }
//
//        return false;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotaID)) return false;
        NotaID<?, ?> notaID = (NotaID<?, ?>) o;
        return Objects.equals(getStudentID(), notaID.getStudentID()) &&
                Objects.equals(getTemaID(), notaID.getTemaID());
    }

    public String toString() {
        return "(" + studentID + ", " + temaID + ")";
    }

    public TIDS getStudentID() {
        return studentID;
    }

    public void setStudentID(TIDS studentID) {
        this.studentID = studentID;
    }

    public TIDT getTemaID() {
        return temaID;
    }

    public void setTemaID(TIDT temaID) {
        this.temaID = temaID;
    }
}