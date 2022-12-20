package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.category.edit

import androidx.compose.ui.focus.FocusState

sealed class EditCategoryEvent {
    data class EnteredName(val value: String) : EditCategoryEvent()
    data class ChangeNameFocus(val focusState: FocusState) : EditCategoryEvent()
    object SaveCategory : EditCategoryEvent()
    object DeleteCategory : EditCategoryEvent()
    data class DeleteDialogVisibleToggle(val visible: Boolean = false) : EditCategoryEvent()
    data class EnteredIconUrl(val value: String) : EditCategoryEvent()
    data class ChangeIconUrlFocus(val focusState: FocusState) : EditCategoryEvent()
}