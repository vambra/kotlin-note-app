package lt.viko.eif.mobiliosiosprogrameles.noteapi.model;

import lt.viko.eif.mobiliosiosprogrameles.noteapi.model.Note;

import java.util.ArrayList;
import java.util.List;

public class Notes {
    private List<Note> notes = new ArrayList<>();

    public Notes() {}
    public Notes(List<Note> notes) {
        this.notes = notes;
    }

    public List<Note> getNotes() {
        return notes;
    }
    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
