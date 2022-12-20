package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.menu.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import lt.viko.eif.mobiliosiosprogrameles.noteapp.R
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Note
import lt.viko.eif.mobiliosiosprogrameles.noteapp.ui.theme.NoteMenuTextColor

@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    items: List<Note>,
    itemTextStyle: TextStyle = TextStyle(fontSize = 20.sp, color = NoteMenuTextColor),
    onItemClick: (Note) -> Unit,
) {
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
                Row() {
                    AsyncImage(
                        model = item.category?.iconUrl,
                        contentDescription = stringResource(id = R.string.category_icon_desc),
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = item.title,
                        style = itemTextStyle,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Divider()
        }
    }
}