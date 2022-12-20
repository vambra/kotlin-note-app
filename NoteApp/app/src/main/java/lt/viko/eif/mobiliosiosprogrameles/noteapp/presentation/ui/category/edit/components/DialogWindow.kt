package lt.viko.eif.mobiliosiosprogrameles.noteapp.presentation.ui.category.edit.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import lt.viko.eif.mobiliosiosprogrameles.noteapp.R
import lt.viko.eif.mobiliosiosprogrameles.noteapp.ui.theme.AddNoteTextColor
import lt.viko.eif.mobiliosiosprogrameles.noteapp.ui.theme.DialogBackgroundColor

@Composable
fun DialogueWindow(
    setShowDialog: (Boolean) -> Unit,
    onConfirmAction: () -> Unit
) {

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = DialogBackgroundColor
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.category_delete_dialog_message),
                            style = TextStyle(
                                fontSize = 20.sp,
                                color = AddNoteTextColor,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                onConfirmAction()
                                setShowDialog(false)
                            },
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth(0.5f)
                                .padding(end = 10.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.category_delete_dialog_message_yes),
                                style = TextStyle(fontSize = 20.sp, color = AddNoteTextColor)
                            )
                        }
                        Button(
                            onClick = {
                                setShowDialog(false)
                            },
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth(1f)
                                .padding(start = 10.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.category_delete_dialog_message_no),
                                style = TextStyle(fontSize = 20.sp, color = AddNoteTextColor)
                            )
                        }
                    }
                }
            }
        }
    }
}