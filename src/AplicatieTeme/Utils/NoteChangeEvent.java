package AplicatieTeme.Utils;

import AplicatieTeme.Domain.Nota;

public class NoteChangeEvent implements Event {
    private final ChangeEventType type;
    private final Nota data;
    private Nota oldData;

    public NoteChangeEvent(ChangeEventType type, Nota data) {
        this.type = type;
        this.data = data;
    }

    public NoteChangeEvent(ChangeEventType type, Nota data, Nota oldData) {
        this.type = type;
        this.data = data;
        this.oldData = oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Nota getData() {
        return data;
    }

    public Nota getOldData() {
        return oldData;
    }
}
