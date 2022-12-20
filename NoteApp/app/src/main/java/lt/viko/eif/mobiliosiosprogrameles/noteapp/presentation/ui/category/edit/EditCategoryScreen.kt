package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.category.edit

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.category.edit.components.CategoryTextField
import lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.category.edit.components.DialogueWindow
import lt.viko.eif.mobiliosiosprogrameles.noteapp.ui.theme.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditCategoryScreen(
    navController: NavController,
    viewModel: EditCategoryViewModel = hiltViewModel(),
    id: Int?
) {
    var idState = viewModel.categoryId.value
    val nameState = viewModel.categoryName.value
    val iconUrlState = viewModel.categoryIconUrl.value

    val scaffoldState = rememberScaffoldState()

    val errorMessageEmptyName = stringResource(R.string.note_error_empty_name)
    val errorMessageDuplicateName = stringResource(R.string.note_error_duplicate_name)
    val errorMessageGeneral = stringResource(R.string.category_save_failed)

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is EditCategoryViewModel.UIEvent.ShowSnackbar -> {
                    val snackMessage: String = when (event.message) {
                        "empty name" -> errorMessageEmptyName
                        "duplicate name" -> errorMessageDuplicateName
                        else -> errorMessageGeneral
                    }
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = snackMessage
                    )
                }
                is EditCategoryViewModel.UIEvent.SaveCategory -> {
                    navController.navigate(Route.CATEGORY_MENU_SCREEN) {
                        popUpTo(Route.CATEGORY_MENU_SCREEN) { inclusive = true }
                    }

                }
                EditCategoryViewModel.UIEvent.DeleteCategory -> {
                    navController.navigate(Route.CATEGORY_MENU_SCREEN) {
                        popUpTo(Route.CATEGORY_MENU_SCREEN) { inclusive = true }
                    }
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            Column() {
                FloatingActionButton(
                    onClick = {
                        viewModel.onEvent(EditCategoryEvent.SaveCategory)
                    },
                    backgroundColor = FloatingActionButtonColor
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = stringResource(R.string.save_note_desc),
                        tint = FloatingActionButtonIconColor
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                idState.id?.let {
                    FloatingActionButton(
                        onClick = {
                            viewModel.onEvent(EditCategoryEvent.DeleteDialogVisibleToggle(true))
                        },
                        backgroundColor = FloatingActionButtonColor
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.delete_note_desc),
                            tint = FloatingActionButtonIconColor
                        )
                    }
                }
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
        if (viewModel.showDeleteDialog.value.isVisible) {
            DialogueWindow(
                setShowDialog = { viewModel.onEvent(EditCategoryEvent.DeleteDialogVisibleToggle(it)) },
                onConfirmAction = { viewModel.onEvent(EditCategoryEvent.DeleteCategory) }
            )
        }
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
                CategoryTextField(
                    text = nameState.text,
                    hint = stringResource(R.string.category_name_hint),
                    onValueChange = { viewModel.onEvent(EditCategoryEvent.EnteredName(it)) },
                    onFocusChange = { viewModel.onEvent(EditCategoryEvent.ChangeNameFocus(it)) },
                    isHintVisible = nameState.isHintVisible,
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 20.sp, color = AddNoteTextColor)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Divider()
                Spacer(modifier = Modifier.height(10.dp))
                Row() {
                    AsyncImage(
                        model = iconUrlState.text,
                        contentDescription = stringResource(id = R.string.category_icon_desc),
                        modifier = Modifier
                            .size(30.dp)
                            .padding(top = 5.dp)
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    CategoryTextField(
                        text = iconUrlState.text,
                        hint = stringResource(id = R.string.category_icon_hint),
                        onValueChange = { viewModel.onEvent(EditCategoryEvent.EnteredIconUrl(it)) },
                        onFocusChange = { viewModel.onEvent(EditCategoryEvent.ChangeIconUrlFocus(it)) },
                        isHintVisible = iconUrlState.isHintVisible,
                        singleLine = false,
                        textStyle = TextStyle(fontSize = 20.sp, color = AddNoteTextColor)
                    )
                }
            }
        }
    }
}