package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.components.drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lt.viko.eif.mobiliosiosprogrameles.noteapp.R
import lt.viko.eif.mobiliosiosprogrameles.noteapp.ui.theme.DrawerHeaderTextColor
import lt.viko.eif.mobiliosiosprogrameles.noteapp.ui.theme.DrawerIconColor
import lt.viko.eif.mobiliosiosprogrameles.noteapp.ui.theme.DrawerTextColor

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.drawer_header),
            fontSize = 30.sp,
            color = DrawerHeaderTextColor
        )
    }
}

@Composable
fun DrawerBodyNavigationList(
    modifier: Modifier = Modifier,
    items: List<MenuItem>,
    onItemClick: (MenuItem) -> Unit
) {
    val itemTextStyle = TextStyle(
        fontSize = 18.sp,
        color = DrawerTextColor
    )

    LazyColumn(modifier) {
        items(items) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(item)
                    }
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.contentDescription,
                    tint = DrawerIconColor
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.title,
                    style = itemTextStyle
                )
            }
        }
    }
}

@Composable
fun DrawerBodyActionButton(
    title: String,
    contentDescription: String,
    icon: ImageVector,
    onItemClick: () -> Unit,
) {
    val itemTextStyle = TextStyle(
        fontSize = 18.sp,
        color = DrawerTextColor
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick()
            }
            .padding(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = DrawerIconColor
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = itemTextStyle
        )
    }
}