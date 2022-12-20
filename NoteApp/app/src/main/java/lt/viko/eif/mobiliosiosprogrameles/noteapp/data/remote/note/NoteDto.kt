package lt.viko.eif.mobiliosiosprogrameles.noteapp.data.remote.note

import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Note;

data class NoteDto(

    @com.squareup.moshi.Json(name = "id")
    val id: Int?,
    @com.squareup.moshi.Json(name = "category")
    val category: CategoryDto,
    @com.squareup.moshi.Json(name = "title")
    val title: String,
    @com.squareup.moshi.Json(name = "text")
    val text: String,
    @com.squareup.moshi.Json(name = "date")
    val date: Long
) {
    fun toNote(): Note {
        return Note(
            id = id,
            category = category.toCategory(),
            title = title,
            text = text,
            date = date
        )
    }
}