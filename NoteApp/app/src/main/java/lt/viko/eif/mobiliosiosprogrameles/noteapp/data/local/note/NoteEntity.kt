package lt.viko.eif.mobiliosiosprogrameles.noteapp.data.local.note

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Category
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Note

@Entity(tableName = "notes_table")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val noteId: Int? = null,
    @Embedded val category: Category,
    val title: String,
    val text: String,
    val date: Long
) {
    fun toNote(): Note {
        return Note(
            id = noteId,
            category = category,
            title = title,
            text = text,
            date = date
        )
    }
}