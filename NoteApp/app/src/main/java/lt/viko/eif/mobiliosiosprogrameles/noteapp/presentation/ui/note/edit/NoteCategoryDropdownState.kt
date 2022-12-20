package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.edit

import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Category

data class NoteCategoryDropdownState(
    val categoryList: List<Category> = emptyList(),
    val selectedCategory: Category? = null,
    val isExpanded: Boolean = false
)