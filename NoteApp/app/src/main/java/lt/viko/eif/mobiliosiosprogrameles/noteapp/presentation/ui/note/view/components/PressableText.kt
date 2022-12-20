package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.note.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun PressableText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle,
    onItemClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clickable {
                onItemClick()
            }) {
        Text(
            text = text,
            style = textStyle,
        )
    }
}