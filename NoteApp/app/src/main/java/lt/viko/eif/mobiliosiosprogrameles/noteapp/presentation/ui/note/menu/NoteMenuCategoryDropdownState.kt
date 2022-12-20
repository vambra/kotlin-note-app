package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.menu

import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Category

data class NoteMenuCategoryDropdownState(
    var categoryList: List<Category?> = emptyList(),
    val selectedCategory: Category? = null,
    val isExpanded: Boolean = false
)