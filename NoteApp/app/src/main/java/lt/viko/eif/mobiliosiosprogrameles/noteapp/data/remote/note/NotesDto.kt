package lt.viko.eif.mobiliosiosprogrameles.noteapp.data.remote.note

import com.squareup.moshi.Json
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Note;

data class NotesDto(

    @Json(name = "notes")
    val notes: List<NoteDto>
) {
    fun toNotesList(): List<Note> {
        var notesList = arrayListOf<Note>()
        for (note in notes) {
            notesList.add(note.toNote())
        }
        return notesList
    }
}