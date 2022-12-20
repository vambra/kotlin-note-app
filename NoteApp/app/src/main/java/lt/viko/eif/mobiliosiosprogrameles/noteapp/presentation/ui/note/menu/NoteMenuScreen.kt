package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.menu

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import lt.viko.eif.mobiliosiosprogrameles.noteapp.R
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.util.Route
import lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.components.drawer.*
import lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.menu.components.DropDownMenu
import lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.menu.components.NoteItem
import lt.viko.eif.mobiliosiosprogrameles.noteapp.ui.theme.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteMenuScreen(
    navController: NavController,
    viewModel: NoteMenuViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    var state = viewModel.state.notes
    val categoryDropdownState = viewModel.categoryDropdown

    val drawerItemList = listOf(
        MenuItem(
            id = Route.SPLASH_SCREEN,
            title = stringResource(R.string.drawer_splash),
            contentDescription = stringResource(R.string.drawer_splash_desc),
            icon = Icons.Default.Star,
        ),
        MenuItem(
            id = Route.CATEGORY_MENU_SCREEN,
            title = stringResource(R.string.drawer_category),
            contentDescription = stringResource(R.string.drawer_category_desc),
            icon = Icons.Default.List,
        )
    )

    val errorMessageApiFetchGeneral = stringResource(R.string.api_fetch_error_general)
    val errorMessageApiFetchHttp = stringResource(R.string.api_fetch_error_http)
    val errorMessageApiFetchIO = stringResource(R.string.api_fetch_error_io)

    viewModel.getNotes(withApiCall = false)
    viewModel.getCategories()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is NoteMenuViewModel.UIEvent.ShowSnackbar -> {
                    val snackMessage: String = when (event.message) {
                        "API fetch HttpException" -> errorMessageApiFetchHttp
                        "API fetch IOException" -> errorMessageApiFetchIO
                        "API fetch Exception" -> errorMessageApiFetchGeneral
                        else -> errorMessageApiFetchGeneral
                    }
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = snackMessage
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                title = stringResource(R.string.note_menu_header),
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DrawerBackgroundColor)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    DrawerHeader()
                    DrawerBodyNavigationList(items = drawerItemList, onItemClick = {
                        navController.navigate(it.id)
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    })
                    Divider()
                    DrawerBodyActionButton(
                        title = stringResource(R.string.drawer_api_upload),
                        contentDescription = stringResource(R.string.drawer_api_upload_desc),
                        icon = Icons.Default.KeyboardArrowUp,
                        onItemClick = {
                            scope.launch {
                                scaffoldState.drawerState.close()
                                viewModel.uploadNotes()
                            }
                        }
                    )
                    DrawerBodyActionButton(
                        title = stringResource(R.string.drawer_api_fetch),
                        contentDescription = stringResource(R.string.drawer_api_fetch_desc),
                        icon = Icons.Default.Refresh,
                        onItemClick = {
                            scope.launch {
                                scaffoldState.drawerState.close()
                                viewModel.getNotes(withApiCall = true)

                            }
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Route.NOTE_ADD_SCREEN + "/-1")
                },
                backgroundColor = FloatingActionButtonColor
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_note_desc),
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
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(AppBackgroundColor)
            ) {
                DropDownMenu(
                    dropdownExpanded = categoryDropdownState.isExpanded,
                    dropdownCategories = categoryDropdownState.categoryList,
                    dropdownSelected = categoryDropdownState.selectedCategory,
                    onCategoryChange = { viewModel.onEvent(NoteMenuEvent.ChosenCategory(it)) },
                    onExpandChange = {
                        viewModel.onEvent(
                            NoteMenuEvent.CategoryDropdownExpandToggle(
                                it
                            )
                        )
                    },
                    emptyText = stringResource(R.string.all_categories)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                NoteItem(
                    items = state,
                    onItemClick = {
                        navController.navigate(Route.NOTE_VIEW_SCREEN + "/${it.id}")
                    }
                )
            }
        }
    }
}