package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.category.menu

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
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.util.Resource
import javax.inject.Inject

@HiltViewModel
class CategoryMenuViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases
) : ViewModel() {

    var state by mutableStateOf(CategoriesState())
        private set


    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
    }

    init {
        getCategories()
    }

    fun getCategories() {
        categoryUseCases.getCategoriesUseCase()
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(
                            categories = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            categories = result.data ?: emptyList(),
                            isLoading = false
                        )
                        if (!result.message.isNullOrEmpty())
                            _eventFlow.emit(UIEvent.ShowSnackbar(result.message))
                    }
                    is Resource.Loading -> {
                        state = state.copy(
                            categories = result.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }
}
