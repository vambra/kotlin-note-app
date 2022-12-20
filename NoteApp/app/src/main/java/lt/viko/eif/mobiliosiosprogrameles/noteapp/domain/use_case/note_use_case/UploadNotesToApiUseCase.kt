package lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.use_case.note_use_case

import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Note
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.repository.NoteRepository
import javax.inject.Inject

class UploadNotesToApiUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(notes: List<Note>) {
        return repository.uploadNotesToApi(notes)
    }
}