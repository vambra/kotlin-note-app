package lt.viko.eif.mobiliosiosprogrameles.noteapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import lt.viko.eif.mobiliosiosprogrameles.noteapp.data.local.category.CategoryDao
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.repository.NoteRepository
import lt.viko.eif.mobiliosiosprogrameles.noteapp.data.local.note.NoteDao
import lt.viko.eif.mobiliosiosprogrameles.noteapp.data.remote.note.NoteApi
import lt.viko.eif.mobiliosiosprogrameles.noteapp.data.remote.note.NotesDto
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Note
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.util.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao,
    private val categoryDao: CategoryDao,
    private val api: NoteApi
) : NoteRepository {
    override fun getAllNotes(
        withApiCall: Boolean,
        categoryId: Int?
    ): Flow<Resource<List<Note>>> = flow {

        emit(Resource.Loading())

        var notes = dao.getAllNotes()
            .sortedByDescending { it.date }
            .map { it.toNote() }
        if (categoryId != null)
            notes = notes.filter { it.category!!.id == categoryId }

        if (withApiCall) {
            emit(Resource.Loading(data = notes))
            try {
                var apiNotes = api.getNotes().body()?.toNotesList()
                if (apiNotes != null) {
                    for (note in apiNotes) {
                        categoryDao.insertCategoryIgnoreDupes(note.category!!.toCategoryEntity())
                        note.category =
                            categoryDao.getCategoryById(note.category!!.id!!)!!.toCategory()
                        dao.insertNoteIgnoreDupes(note.toNoteEntity())
                    }
                    notes = dao.getAllNotes()
                        .sortedByDescending { it.date }
                        .map { it.toNote() }
                    if (categoryId != null)
                        notes = notes.filter { it.category!!.id == categoryId }
                }
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        message = "API fetch HttpException",
                        data = notes
                    )
                )
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = "API fetch IOException",
                        data = notes
                    )
                )
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        message = "API fetch Exception",
                        data = notes
                    )
                )
            }
        }
        emit(Resource.Success(data = notes))
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)?.toNote()
    }

    override suspend fun insertNote(note: Note): Int {
        val rowId = dao.insertNote(note.toNoteEntity())
        return rowId.toInt()
    }

    override suspend fun insertNoteIgnoreDupes(note: Note): Int {
        val rowId = dao.insertNote(note.toNoteEntity())
        return rowId.toInt()
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note.toNoteEntity())
    }

    override suspend fun uploadNotesToApi(notes: List<Note>) {
        api.setNotes(NotesDto(notes = notes.map { it.toNoteDto() }))
    }
}