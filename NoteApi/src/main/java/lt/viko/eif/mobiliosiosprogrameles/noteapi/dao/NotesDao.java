package lt.viko.eif.mobiliosiosprogrameles.noteapi.dao;

import lt.viko.eif.mobiliosiosprogrameles.noteapi.model.Category;
import lt.viko.eif.mobiliosiosprogrameles.noteapi.model.Note;
import lt.viko.eif.mobiliosiosprogrameles.noteapi.model.Notes;
import org.springframework.stereotype.Repository;

@Repository
public class NotesDao {
    private static Notes notes = new Notes();

    static
    {
        Category category1 = new Category(1, "category1", "https://www.iconsdb.com/icons/preview/white/note-xxl.png");
        notes.getNotes().add(new Note(1, category1, "TestTitleFromAPI", "TestTextFromAPI", 0));

        Category category2 = new Category(2, "category2", "https://www.iconsdb.com/icons/preview/soylent-red/instagram-xxl.png");
        notes.getNotes().add(new Note(2, category2, "TestTitleFromAPI-2", "TestTextFromAPI-2", 0));
    }

    public Notes getNotes(){
        return notes;
    }
    public void setNotes(Notes notes){
        this.notes = notes;
    }
}
