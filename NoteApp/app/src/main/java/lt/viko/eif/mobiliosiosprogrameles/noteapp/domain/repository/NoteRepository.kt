package lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.repository

import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Note
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(withApiCall: Boolean = false, categoryId: Int? = null): Flow<Resource<List<Note>>>
    suspend fun getNoteById(id: Int): Note?
    suspend fun insertNote(note: Note): Int
    suspend fun insertNoteIgnoreDupes(note: Note): Int
    suspend fun deleteNote(note: Note)
    suspend fun uploadNotesToApi(notes: List<Note>)
}

