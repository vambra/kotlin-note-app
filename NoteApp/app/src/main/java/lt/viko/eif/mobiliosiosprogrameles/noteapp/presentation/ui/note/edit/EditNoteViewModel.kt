package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.InvalidNoteException
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Note
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.use_case.category_use_case.CategoryUseCases
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.use_case.note_use_case.NoteUseCases
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.util.Resource
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val categoryUseCases: CategoryUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _noteId = mutableStateOf(NoteIdState())
    val noteId: State<NoteIdState> = _noteId

    private val _noteTitle = mutableStateOf(NoteTextFieldState())
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteText = mutableStateOf(NoteTextFieldState())
    val noteText: State<NoteTextFieldState> = _noteText

    var noteCategory by mutableStateOf(NoteCategoryDropdownState())
        private set

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun getCategories() {
        categoryUseCases.getCategoriesUseCase()
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        noteCategory = noteCategory.copy(
                            categoryList = result.data ?: emptyList(),
                        )
                    }
                    is Resource.Error -> {
                        noteCategory = noteCategory.copy(
                            categoryList = result.data ?: emptyList(),
                        )
                        if (!result.message.isNullOrEmpty())
                            _eventFlow.emit(UIEvent.ShowSnackbar(result.message))
                    }
                    is Resource.Loading -> {
                        noteCategory = noteCategory.copy(
                            categoryList = result.data ?: emptyList(),
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    init {
        savedStateHandle.get<Int>("id")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNoteByIdUseCase(noteId)?.also { note ->
                        _noteId.value = _noteId.value.copy(
                            id = note.id
                        )
                        _noteTitle.value = _noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteText.value = _noteText.value.copy(
                            text = note.text,
                            isHintVisible = false
                        )
                        noteCategory = noteCategory.copy(
                            selectedCategory = categoryUseCases.getCategoryByIdUseCase(note.category!!.id!!)
                        )
                    }
                }
            }
        }
        getCategories()
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
        object SaveNote : UIEvent()
    }


    fun onEvent(event: EditNoteEvent) {
        when (event) {
            is EditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }
            is EditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteTitle.value.text.isBlank()
                )
            }
            is EditNoteEvent.EnteredText -> {
                _noteText.value = _noteText.value.copy(
                    text = event.value
                )
            }
            is EditNoteEvent.ChangeTextFocus -> {
                _noteText.value = _noteText.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _noteText.value.text.isBlank()
                )
            }
            is EditNoteEvent.CategoryDropdownExpandToggle -> {
                noteCategory = noteCategory.copy(
                    isExpanded = !noteCategory.isExpanded
                )
            }
            is EditNoteEvent.ChosenCategory -> {
                noteCategory = noteCategory.copy(
                    selectedCategory = event.category
                )
            }
            is EditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        val newNoteId = noteUseCases.insertNoteUseCase(
                            Note(
                                category = noteCategory.selectedCategory,
                                title = _noteTitle.value.text,
                                text = _noteText.value.text,
                                date = System.currentTimeMillis(),
                                id = _noteId.value.id // if updating note
                            )
                        )
                        _noteId.value = _noteId.value.copy(
                            id = newNoteId
                        )
                        _eventFlow.emit(UIEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                message = e.message ?: "unknown error",
                            )
                        )
                    }
                }
            }
        }
    }
}