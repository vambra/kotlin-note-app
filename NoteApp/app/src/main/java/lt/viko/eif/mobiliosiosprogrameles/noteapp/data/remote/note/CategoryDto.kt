package lt.viko.eif.mobiliosiosprogrameles.noteapp.data.remote.note

import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Category

data class CategoryDto(

    @com.squareup.moshi.Json(name = "id")
    val id: Int,
    @com.squareup.moshi.Json(name = "name")
    val name: String,
    @com.squareup.moshi.Json(name = "iconUrl")
    val iconUrl: String?
) {
    fun toCategory(): Category {
        return Category(
            id = id,
            name = name,
            iconUrl = iconUrl
        )
    }
}