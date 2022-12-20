package lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.use_case.note_use_case

import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Note
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.repository.NoteRepository
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(withApiCall: Boolean, categoryId: Int?): Flow<Resource<List<Note>>> {
        return repository.getAllNotes(withApiCall, categoryId)
    }
}