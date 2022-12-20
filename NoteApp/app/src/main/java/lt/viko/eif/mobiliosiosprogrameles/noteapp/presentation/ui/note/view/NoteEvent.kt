package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.view

import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Note

sealed class NoteEvent {
    data class DeleteNote(val note: Note) : NoteEvent()
}