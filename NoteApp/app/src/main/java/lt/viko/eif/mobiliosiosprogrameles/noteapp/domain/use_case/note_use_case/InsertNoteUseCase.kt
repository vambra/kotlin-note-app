package lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.use_case.note_use_case

import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Note
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.repository.NoteRepository
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.InvalidNoteException
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note): Int? {
        if (note.title.isBlank()) {
            throw InvalidNoteException("empty title")
        }
        if (note.category == null) {
            throw InvalidNoteException("empty category")
        }
        if (note.text.isBlank()) {
            throw InvalidNoteException("empty text")
        }
        return repository.insertNote(note)
    }
}