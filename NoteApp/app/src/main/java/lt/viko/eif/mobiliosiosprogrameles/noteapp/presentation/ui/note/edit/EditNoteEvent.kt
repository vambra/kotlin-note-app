package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.edit

import androidx.compose.ui.focus.FocusState
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Category

sealed class EditNoteEvent {
    data class EnteredTitle(val value: String) : EditNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : EditNoteEvent()
    data class EnteredText(val value: String) : EditNoteEvent()
    data class ChangeTextFocus(val focusState: FocusState) : EditNoteEvent()
    data class ChosenCategory(val category: Category) : EditNoteEvent()
    data class CategoryDropdownExpandToggle(val expanded: Boolean) : EditNoteEvent()
    object SaveNote : EditNoteEvent()
}