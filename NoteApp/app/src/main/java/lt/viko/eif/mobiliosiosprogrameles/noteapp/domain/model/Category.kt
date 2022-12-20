package lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model

import lt.viko.eif.mobiliosiosprogrameles.noteapp.data.local.category.CategoryEntity
import lt.viko.eif.mobiliosiosprogrameles.noteapp.data.remote.note.CategoryDto

data class Category(
    val id: Int? = null,
    val name: String? = null,
    val iconUrl: String? = null
) {
    fun toCategoryEntity(): CategoryEntity {
        return CategoryEntity(
            categoryId = id,
            name = name!!,
            iconUrl = iconUrl
        )
    }

    fun toCategoryDto(): CategoryDto {
        return CategoryDto(
            id = id!!,
            name = name!!,
            iconUrl = iconUrl
        )
    }
}

class InvalidCategoryException(message: String) : Exception(message)