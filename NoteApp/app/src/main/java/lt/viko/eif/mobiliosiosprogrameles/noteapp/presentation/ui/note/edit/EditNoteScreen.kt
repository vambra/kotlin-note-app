package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.edit

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collectLatest
import lt.viko.eif.mobiliosiosprogrameles.noteapp.R
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.util.Route
import lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.edit.components.DropDownMenu
import lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.edit.components.NoteTextField
import lt.viko.eif.mobiliosiosprogrameles.noteapp.ui.theme.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditNoteScreen(
    navController: NavController,
    viewModel: EditNoteViewModel = hiltViewModel(),
    id: Int?
) {
    var idState = viewModel.noteId.value
    val titleState = viewModel.noteTitle.value
    val textState = viewModel.noteText.value
    val categoryState = viewModel.noteCategory

    val scaffoldState = rememberScaffoldState()

    val errorMessageEmptyTitle = stringResource(R.string.note_error_empty_title)
    val errorMessageEmptyText = stringResource(R.string.note_error_empty_text)
    val errorCategoryEmptyText = stringResource(R.string.note_error_empty_category)
    val errorMessageSaveFailed = stringResource(R.string.note_save_failed)

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is EditNoteViewModel.UIEvent.ShowSnackbar -> {
                    val snackMessage: String = when (event.message) {
                        "empty title" -> errorMessageEmptyTitle
                        "empty text" -> errorMessageEmptyText
                        "empty category" -> errorCategoryEmptyText
                        else -> errorMessageSaveFailed
                    }
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = snackMessage
                    )
                }
                is EditNoteViewModel.UIEvent.SaveNote -> {
                    idState = viewModel.noteId.value
                    navController.navigate(Route.NOTE_VIEW_SCREEN + "/${idState.id}") {
                        popUpTo(Route.NOTE_MENU_SCREEN) { inclusive = false }
                    }

                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(EditNoteEvent.SaveNote)
                },
                backgroundColor = FloatingActionButtonColor
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(R.string.save_note_desc),
                    tint = FloatingActionButtonIconColor
                )
            }
        },
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    backgroundColor = SnackbarBackgroundColor,
                    contentColor = SnackbarContentColor,
                    snackbarData = data
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .background(AppBackgroundColor)
            ) {
                Row() {
                    AsyncImage(
                        model = categoryState.selectedCategory?.iconUrl,
                        contentDescription = stringResource(id = R.string.category_icon_desc),
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Column() {
                        NoteTextField(
                            text = titleState.text,
                            hint = stringResource(R.string.title_hint),
                            onValueChange = { viewModel.onEvent(EditNoteEvent.EnteredTitle(it)) },
                            onFocusChange = { viewModel.onEvent(EditNoteEvent.ChangeTitleFocus(it)) },
                            isHintVisible = titleState.isHintVisible,
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 20.sp, color = AddNoteTextColor)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        DropDownMenu(
                            dropdownExpanded = categoryState.isExpanded,
                            dropdownCategories = categoryState.categoryList,
                            dropdownSelected = categoryState.selectedCategory,
                            onCategoryChange = { viewModel.onEvent(EditNoteEvent.ChosenCategory(it)) },
                            onExpandChange = {
                                viewModel.onEvent(
                                    EditNoteEvent.CategoryDropdownExpandToggle(
                                        it
                                    )
                                )
                            },
                            emptyText = stringResource(R.string.category_dropdown_default)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))
                NoteTextField(
                    text = textState.text,
                    hint = stringResource(R.string.text_hint),
                    onValueChange = { viewModel.onEvent(EditNoteEvent.EnteredText(it)) },
                    onFocusChange = { viewModel.onEvent(EditNoteEvent.ChangeTextFocus(it)) },
                    isHintVisible = textState.isHintVisible,
                    textStyle = TextStyle(fontSize = 16.sp, color = AddNoteTextColor),
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }
}