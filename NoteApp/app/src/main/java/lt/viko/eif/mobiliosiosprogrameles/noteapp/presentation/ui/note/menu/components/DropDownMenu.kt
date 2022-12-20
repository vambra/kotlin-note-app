package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.menu.components

import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Size
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import coil.compose.AsyncImage
import lt.viko.eif.mobiliosiosprogrameles.noteapp.R
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Category
import lt.viko.eif.mobiliosiosprogrameles.noteapp.ui.theme.AddNoteIconColor
import lt.viko.eif.mobiliosiosprogrameles.noteapp.ui.theme.AddNoteTextColor

@Composable
fun DropDownMenu(
    dropdownExpanded: Boolean,
    dropdownCategories: List<Category?>,
    dropdownSelected: Category?,
    onExpandChange: (Boolean) -> Unit,
    onCategoryChange: (Category?) -> Unit,
    emptyText: String
) {

    var dropdownFieldSize by remember { mutableStateOf(Size.Zero) }

    var dropdownCategoryList = dropdownCategories.toMutableList()
    dropdownCategoryList.add(0, null)

    val icon = if (dropdownExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    dropdownFieldSize = coordinates.size.toSize()
                }
                .clickable { onExpandChange(!dropdownExpanded) },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = dropdownSelected?.name ?: emptyText,
                style = TextStyle(fontSize = 20.sp, color = AddNoteTextColor)
            )
            Icon(
                imageVector = icon,
                contentDescription = stringResource(R.string.toggle_dropdown_desc),
                tint = AddNoteIconColor
            )
        }
        DropdownMenu(
            expanded = dropdownExpanded,
            onDismissRequest = { onExpandChange(false) },
            modifier = Modifier
                .width(with(LocalDensity.current) { dropdownFieldSize.width.toDp() })
        ) {
            dropdownCategoryList.forEach { category ->
                val textToShow =
                    if (category == null) emptyText
                    else category.name!!
                DropdownMenuItem(onClick = {
                    onCategoryChange(category)
                    onExpandChange(!dropdownExpanded)
                }) {
                    AsyncImage(
                        model = category?.iconUrl,
                        contentDescription = stringResource(id = R.string.category_icon_desc),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(
                        text = textToShow,
                        style = TextStyle(fontSize = 20.sp, color = AddNoteTextColor)
                    )
                }
            }
        }
    }
}
