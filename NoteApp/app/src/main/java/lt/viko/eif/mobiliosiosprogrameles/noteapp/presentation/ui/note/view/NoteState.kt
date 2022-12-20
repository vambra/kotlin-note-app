package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.view

import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Note

data class NoteState(
    val note: Note? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)