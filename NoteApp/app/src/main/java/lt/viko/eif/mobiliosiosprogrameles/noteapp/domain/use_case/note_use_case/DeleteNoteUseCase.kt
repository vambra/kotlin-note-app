package lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.use_case.note_use_case

import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Note
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        return repository.deleteNote(note)
    }
}