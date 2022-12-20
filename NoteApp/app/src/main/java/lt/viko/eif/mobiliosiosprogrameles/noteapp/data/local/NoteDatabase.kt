package lt.viko.eif.mobiliosiosprogrameles.noteapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import lt.viko.eif.mobiliosiosprogrameles.noteapp.data.local.category.CategoryDao
import lt.viko.eif.mobiliosiosprogrameles.noteapp.data.local.category.CategoryEntity
import lt.viko.eif.mobiliosiosprogrameles.noteapp.data.local.note.NoteDao
import lt.viko.eif.mobiliosiosprogrameles.noteapp.data.local.note.NoteEntity

@Database(
    entities = [NoteEntity::class, CategoryEntity::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao
    abstract val categoryDao: CategoryDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}