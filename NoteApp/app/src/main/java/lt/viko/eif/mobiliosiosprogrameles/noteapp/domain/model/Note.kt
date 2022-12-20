package lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model

import lt.viko.eif.mobiliosiosprogrameles.noteapp.data.local.note.NoteEntity
import lt.viko.eif.mobiliosiosprogrameles.noteapp.data.remote.note.NoteDto

data class Note(
    val id: Int? = null,
    var category: Category? = null,
    val title: String,
    val text: String,
    val date: Long,
) {
    fun toNoteEntity(): NoteEntity {
        return NoteEntity(
            noteId = id,
            category = category!!,
            title = title,
            text = text,
            date = date
        )
    }

    fun toNoteDto(): NoteDto {
        return NoteDto(
            id = id,
            category = category!!.toCategoryDto(),
            title = title,
            text = text,
            date = date
        )
    }
}

class InvalidNoteException(message: String) : Exception(message)