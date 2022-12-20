package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.category.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Category
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.InvalidCategoryException
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.use_case.category_use_case.CategoryUseCases
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.use_case.note_use_case.NoteUseCases
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.util.Resource
import javax.inject.Inject

@HiltViewModel
class EditCategoryViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases,
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _categoryId = mutableStateOf(CategoryIdState())
    val categoryId: State<CategoryIdState> = _categoryId

    private val _categoryName = mutableStateOf(CategoryTextFieldState())
    val categoryName: State<CategoryTextFieldState> = _categoryName

    private val _categoryIconUrl = mutableStateOf(CategoryTextFieldState())
    val categoryIconUrl: State<CategoryTextFieldState> = _categoryIconUrl

    var showDeleteDialog = mutableStateOf(CategoryDialogState())

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
        savedStateHandle.get<Int>("id")?.let { categoryId ->
            if (categoryId != -1) {
                viewModelScope.launch {
                    categoryUseCases.getCategoryByIdUseCase(categoryId)?.also { category ->
                        _categoryId.value = _categoryId.value.copy(
                            id = category.id
                        )
                        _categoryName.value = _categoryName.value.copy(
                            text = category.name ?: "",
                            isHintVisible = false
                        )
                        _categoryIconUrl.value = _categoryIconUrl.value.copy(
                            text = category.iconUrl ?: "",
                            isHintVisible = false
                        )
                    }
                }
            }
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
        object SaveCategory : UIEvent()
        object DeleteCategory : UIEvent()
    }

    fun onEvent(event: EditCategoryEvent) {
        when (event) {
            is EditCategoryEvent.EnteredName -> {
                _categoryName.value = _categoryName.value.copy(
                    text = event.value
                )
            }
            is EditCategoryEvent.ChangeNameFocus -> {
                _categoryName.value = _categoryName.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _categoryName.value.text.isBlank()
                )
            }
            is EditCategoryEvent.SaveCategory -> {
                viewModelScope.launch {
                    try {
                        val newCategoryId = categoryUseCases.insertCategoryUseCase(
                            Category(
                                name = _categoryName.value.text,
                                iconUrl = _categoryIconUrl.value.text,
                                id = _categoryId.value.id // if updating category
                            )
                        )
                        _categoryId.value = _categoryId.value.copy(
                            id = newCategoryId
                        )
                        _eventFlow.emit(UIEvent.SaveCategory)
                    } catch (e: InvalidCategoryException) {
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                message = e.message ?: "unknown error",
                            )
                        )
                    }
                }
            }
            EditCategoryEvent.DeleteCategory -> {
                viewModelScope.launch {
                    noteUseCases.getNotesUseCase(
                        withApiCall = false,
                        categoryId = _categoryId.value.id!!
                    ).collect() { result ->
                        when (result) {
                            is Resource.Success -> {
                                result.data?.onEach { note ->
                                    noteUseCases.deleteNoteUseCase(note)
                                }
                            }
                            is Resource.Error -> {
                                result.data?.onEach { note ->
                                    noteUseCases.deleteNoteUseCase(note)
                                }
                            }
                            is Resource.Loading -> {
                                result.data?.onEach { note ->
                                    noteUseCases.deleteNoteUseCase(note)
                                }
                            }
                        }
                    }
                    categoryUseCases.deleteCategoryByIdUseCase(_categoryId.value.id!!)
                    _eventFlow.emit(UIEvent.DeleteCategory)
                }
            }
            is EditCategoryEvent.DeleteDialogVisibleToggle -> {
                showDeleteDialog.value = showDeleteDialog.value.copy(
                    isVisible = !showDeleteDialog.value.isVisible
                )
            }
            is EditCategoryEvent.ChangeIconUrlFocus -> {
                _categoryIconUrl.value = _categoryIconUrl.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _categoryIconUrl.value.text.isBlank()
                )
            }
            is EditCategoryEvent.EnteredIconUrl -> {
                _categoryIconUrl.value = _categoryIconUrl.value.copy(
                    text = event.value
                )
            }
        }
    }
}