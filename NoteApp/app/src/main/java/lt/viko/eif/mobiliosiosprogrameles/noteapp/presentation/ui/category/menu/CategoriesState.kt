package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.category.menu

import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Category

data class CategoriesState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)