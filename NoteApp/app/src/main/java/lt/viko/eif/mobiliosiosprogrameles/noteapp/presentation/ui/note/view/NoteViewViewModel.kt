package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.use_case.category_use_case.CategoryUseCases
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.use_case.note_use_case.NoteUseCases
import javax.inject.Inject

@HiltViewModel
class NoteViewViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val categoryUseCases: CategoryUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(NoteState())
        private set

    init {
        savedStateHandle.get<Int>("id")?.let { noteId ->
            viewModelScope.launch {
                noteUseCases.getNoteByIdUseCase(noteId)?.also { note ->
                    val noteCategory = categoryUseCases.getCategoryByIdUseCase(note.category!!.id!!)
                    note.category = noteCategory
                    state = state.copy(note = note)
                }
            }
        }
    }

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNoteUseCase(event.note)
                }
            }
        }
    }
}