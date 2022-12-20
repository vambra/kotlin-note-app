package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.components.drawer

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import lt.viko.eif.mobiliosiosprogrameles.noteapp.R
import lt.viko.eif.mobiliosiosprogrameles.noteapp.ui.theme.AppTopBarColor
import lt.viko.eif.mobiliosiosprogrameles.noteapp.ui.theme.AppTopBarIconColor
import lt.viko.eif.mobiliosiosprogrameles.noteapp.ui.theme.AppTopBarTextColor

@Composable
fun AppBar(
    title: String,
    onNavigationIconClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = TextStyle(fontSize = 24.sp, color = AppTopBarTextColor)
            )
        },
        backgroundColor = AppTopBarColor,
        contentColor = AppTopBarIconColor,
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(R.string.toggle_drawer)
                )
            }
        }
    )
}