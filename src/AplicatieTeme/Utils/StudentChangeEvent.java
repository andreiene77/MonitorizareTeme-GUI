package AplicatieTeme.Utils;

import AplicatieTeme.Domain.Student;

public class StudentChangeEvent implements Event {
    private final ChangeEventType type;
    private final Student data;
    private Student oldData;

    public StudentChangeEvent(ChangeEventType type, Student data) {
        this.type = type;
        this.data = data;
    }

    public StudentChangeEvent(ChangeEventType type, Student data, Student oldData) {
        this.type = type;
        this.data = data;
        this.oldData = oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Student getData() {
        return data;
    }

    public Student getOldData() {
        return oldData;
    }
}
