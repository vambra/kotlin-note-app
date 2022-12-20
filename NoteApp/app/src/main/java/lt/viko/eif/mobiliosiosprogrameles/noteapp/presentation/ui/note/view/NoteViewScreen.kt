package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import lt.viko.eif.mobiliosiosprogrameles.noteapp.R
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.util.Route
import lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.view.components.PressableText
import lt.viko.eif.mobiliosiosprogrameles.noteapp.ui.theme.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteViewScreen(
    navController: NavController,
    viewModel: NoteViewViewModel = hiltViewModel(),
) {
    var state = viewModel.state.note

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            Column() {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Route.NOTE_ADD_SCREEN + "/${state?.id}")
                    },
                    backgroundColor = FloatingActionButtonColor
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit_note_desc),
                        tint = FloatingActionButtonIconColor
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                FloatingActionButton(
                    onClick = {
                        viewModel.onEvent(NoteEvent.DeleteNote(state!!))
                        navController.navigate(Route.NOTE_MENU_SCREEN) {
                            popUpTo(Route.NOTE_MENU_SCREEN) { inclusive = true }
                        }
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
            ) {
                Row() {
                    AsyncImage(
                        model = state?.category?.iconUrl,
                        contentDescription = stringResource(id = R.string.category_icon_desc),
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Column() {
                        PressableText(
                            text = state?.title.toString(),
                            textStyle = TextStyle(fontSize = 20.sp, color = NoteTextColor),
                            onItemClick = { navController.navigate(Route.NOTE_ADD_SCREEN + "/${state?.id}") }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        PressableText(
                            text = state?.category?.name.toString(),
                            textStyle = TextStyle(fontSize = 16.sp, color = NoteTextColor),
                            onItemClick = { navController.navigate(Route.NOTE_ADD_SCREEN + "/${state?.id}") }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))
                PressableText(
                    text = state?.text.toString(),
                    textStyle = TextStyle(fontSize = 16.sp, color = NoteTextColor),
                    onItemClick = { navController.navigate(Route.NOTE_ADD_SCREEN + "/${state?.id}") }
                )
            }
        }
    }
}