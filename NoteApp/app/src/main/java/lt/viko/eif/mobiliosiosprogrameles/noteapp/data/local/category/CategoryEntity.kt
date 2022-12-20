package lt.viko.eif.mobiliosiosprogrameles.noteapp.data.local.category

import androidx.room.Entity
import androidx.room.PrimaryKey
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Category

@Entity(tableName = "category_table")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val categoryId: Int? = null,
    val name: String,
    val iconUrl: String?
) {
    fun toCategory(): Category {
        return Category(
            id = categoryId,
            name = name,
            iconUrl = iconUrl
        )
    }
}