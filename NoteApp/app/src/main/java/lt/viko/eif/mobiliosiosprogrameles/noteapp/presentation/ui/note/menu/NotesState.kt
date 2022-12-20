package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.menu

import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Note

data class NotesState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)