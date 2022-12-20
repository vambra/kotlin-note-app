package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.components.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.util.Route
import lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.category.edit.EditCategoryScreen
import lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.category.menu.CategoryMenuScreen
import lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.view.NoteViewScreen
import lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.menu.NoteMenuScreen
import lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.edit.EditNoteScreen
import lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.splash.SplashScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Route.SPLASH_SCREEN
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Route.NOTE_MENU_SCREEN) {
            NoteMenuScreen(navController = navController)
        }
        composable(
            route = Route.NOTE_VIEW_SCREEN + "/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType; defaultValue = -1 }
            )) {
            NoteViewScreen(navController = navController)
        }
        composable(route = Route.NOTE_ADD_SCREEN + "/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType; defaultValue = -1 }
            )) { backStackEntry ->
            EditNoteScreen(
                navController = navController,
                id = backStackEntry.arguments?.getInt("id")
            )
        }
        composable(route = Route.SPLASH_SCREEN) {
            SplashScreen(navController = navController)
        }
        composable(route = Route.CATEGORY_MENU_SCREEN) {
            CategoryMenuScreen(navController = navController)
        }
        composable(route = Route.CATEGORY_EDIT_SCREEN + "/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType; defaultValue = -1 }
            )) { backStackEntry ->
            EditCategoryScreen(
                navController = navController,
                id = backStackEntry.arguments?.getInt("id")
            )
        }
    }
}
