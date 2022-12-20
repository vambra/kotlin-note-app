package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.menu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.use_case.category_use_case.CategoryUseCases
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.use_case.note_use_case.NoteUseCases
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.util.Resource
import javax.inject.Inject

@HiltViewModel
class NoteMenuViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val categoryUseCases: CategoryUseCases
) : ViewModel() {

    var state by mutableStateOf(NotesState())
        private set

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var categoryDropdown by mutableStateOf(NoteMenuCategoryDropdownState())

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
    }

    init {
        getCategories()
        getNotes(withApiCall = false)
    }

    fun getNotes(withApiCall: Boolean) {
        noteUseCases.getNotesUseCase(
            withApiCall = withApiCall,
            categoryId = categoryDropdown.selectedCategory?.id
        )
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.forEach { note ->
                            note.category = categoryDropdown.categoryList.find {
                                note.category?.id == it?.id
                            }
                        }
                        state = state.copy(
                            notes = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        result.data?.forEach { note ->
                            note.category = categoryDropdown.categoryList.find {
                                note.category?.id == it?.id
                            }
                        }
                        state = state.copy(
                            notes = result.data ?: emptyList(),
                            isLoading = false
                        )
                        if (!result.message.isNullOrEmpty())
                            _eventFlow.emit(UIEvent.ShowSnackbar(result.message))
                    }
                    is Resource.Loading -> {
                        result.data?.forEach { note ->
                            note.category = categoryDropdown.categoryList.find {
                                note.category?.id == it?.id
                            }
                        }
                        state = state.copy(
                            notes = result.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    suspend fun uploadNotes() {
        noteUseCases.uploadNotesToApiUseCase(state.notes)
    }

    fun getCategories() {
        categoryUseCases.getCategoriesUseCase()
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        categoryDropdown = categoryDropdown.copy(
                            categoryList = result.data ?: emptyList()
                        )
                    }
                    is Resource.Error -> {
                        categoryDropdown = categoryDropdown.copy(
                            categoryList = result.data ?: emptyList()
                        )
                        if (!result.message.isNullOrEmpty())
                            _eventFlow.emit(UIEvent.ShowSnackbar(result.message))
                    }
                    is Resource.Loading -> {
                        categoryDropdown = categoryDropdown.copy(
                            categoryList = result.data ?: emptyList()
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun onEvent(event: NoteMenuEvent) {
        when (event) {
            is NoteMenuEvent.CategoryDropdownExpandToggle -> {
                categoryDropdown = categoryDropdown.copy(
                    isExpanded = !categoryDropdown.isExpanded
                )
            }
            is NoteMenuEvent.ChosenCategory -> {
                categoryDropdown = categoryDropdown.copy(
                    selectedCategory = event.category
                )
            }
        }
    }
}
