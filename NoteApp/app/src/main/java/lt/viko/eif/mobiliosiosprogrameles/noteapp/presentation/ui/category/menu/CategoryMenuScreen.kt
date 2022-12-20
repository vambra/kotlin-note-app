package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.category.menu

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
import lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.category.menu.components.CategoryItem
import lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.components.drawer.*
import lt.viko.eif.mobiliosiosprogrameles.noteapp.ui.theme.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CategoryMenuScreen(
    navController: NavController,
    viewModel: CategoryMenuViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    var state = viewModel.state.categories

    val drawerItemList = listOf(
        MenuItem(
            id = Route.SPLASH_SCREEN,
            title = stringResource(R.string.drawer_splash),
            contentDescription = stringResource(R.string.drawer_splash_desc),
            icon = Icons.Default.Star,
        ),
        MenuItem(
            id = Route.NOTE_MENU_SCREEN,
            title = stringResource(R.string.drawer_note),
            contentDescription = stringResource(R.string.drawer_note_desc),
            icon = Icons.Default.List,
        )
    )

    viewModel.getCategories()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is CategoryMenuViewModel.UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                title = stringResource(R.string.category_menu_header),
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
                    DrawerBodyNavigationList(
                        items = drawerItemList,
                        onItemClick = {
                            navController.navigate(it.id)
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Route.CATEGORY_EDIT_SCREEN + "/-1")
                },
                backgroundColor = FloatingActionButtonColor
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_category_desc),
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
                    .padding(16.dp)
                    .background(AppBackgroundColor)
            ) {
                CategoryItem(
                    items = state,
                    onItemClick = {
                        navController.navigate(Route.CATEGORY_EDIT_SCREEN + "/${it.id}")
                    }
                )
            }
        }
    }
}