package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.components.layout.*
import lt.viko.eif.mobiliosiosprogrameles.noteapp.ui.theme.AppBackgroundColor
import lt.viko.eif.mobiliosiosprogrameles.noteapp.ui.theme.NoteAppTheme
import lt.viko.eif.mobiliosiosprogrameles.noteapp.ui.theme.StatusBarColor

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppTheme(
                darkTheme = true
            ) {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(color = StatusBarColor)

                val navController = rememberNavController()

                Scaffold() {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(AppBackgroundColor)
                    )
                    AppNavHost(
                        Modifier.padding(it),
                        navController = navController,
                    )
                }
            }
        }
    }
}