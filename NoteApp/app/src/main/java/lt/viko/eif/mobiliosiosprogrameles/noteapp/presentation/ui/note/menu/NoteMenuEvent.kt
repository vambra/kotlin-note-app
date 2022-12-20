package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.menu

import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Category

sealed class NoteMenuEvent {
    data class ChosenCategory(val category: Category?) : NoteMenuEvent()
    data class CategoryDropdownExpandToggle(val expanded: Boolean) : NoteMenuEvent()
}