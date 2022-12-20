package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.category.menu.components

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
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Category
import lt.viko.eif.mobiliosiosprogrameles.noteapp.ui.theme.NoteMenuTextColor

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    items: List<Category>,
    itemTextStyle: TextStyle = TextStyle(fontSize = 20.sp, color = NoteMenuTextColor),
    onItemClick: (Category) -> Unit,
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
                AsyncImage(
                    model = item?.iconUrl,
                    contentDescription = stringResource(id = R.string.category_icon_desc),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = item.name!!,
                    style = itemTextStyle
                )
            }
            Divider()
        }
    }
}