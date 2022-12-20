package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import lt.viko.eif.mobiliosiosprogrameles.noteapp.R
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.util.Route
import lt.viko.eif.mobiliosiosprogrameles.noteapp.ui.theme.SplashScreenBackgroundColor

@Composable
fun SplashScreen(
    navController: NavController
) {
    LaunchedEffect(key1 = true) {
        delay(1000L)
        navController.navigate(Route.NOTE_MENU_SCREEN) {
            popUpTo(Route.SPLASH_SCREEN) { inclusive = true }
        }

    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SplashScreenBackgroundColor),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = "https://www.iconsdb.com/icons/preview/white/note-2-xxl.png",
            contentDescription = stringResource(R.string.splash_screen_icon_desc),
        )
    }

}
