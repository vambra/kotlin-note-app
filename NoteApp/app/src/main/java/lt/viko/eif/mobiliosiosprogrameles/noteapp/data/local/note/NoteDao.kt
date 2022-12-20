package lt.viko.eif.mobiliosiosprogrameles.noteapp.data.local.note

import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNoteIgnoreDupes(note: NoteEntity): Long

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): List<NoteEntity>

    @Query("SELECT * FROM notes_table WHERE noteId = :id")
    suspend fun getNoteById(id: Int): NoteEntity?
}