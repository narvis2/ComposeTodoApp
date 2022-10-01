package com.example.composetodoapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composetodoapp.R
import com.example.composetodoapp.domain.model.Note
import com.example.composetodoapp.presentation.utils.timeFormatter

@ExperimentalComposeUiApi
@Composable
fun NoteInputText(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    maxLine: Int = 1,
    onTextChange: (String) -> Unit,
    keyboardContainer: SoftwareKeyboardController?,
    onImeAction: () -> Unit = {}
) {
    TextField(
        value = text,
        onValueChange = onTextChange,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
        maxLines = maxLine,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardContainer?.hide()
        }),
        modifier = modifier
    )
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier, text: String, enabled: Boolean = true, onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, if (enabled) Color.Red else Color.Gray),
        enabled = enabled,
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
    ) {
        Text(text = text)
    }
}

@Composable
fun DeleteView(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
        OutlinedButton(
            onClick = onClick,
            shape = RoundedCornerShape(
                topStart = 5.dp,
                topEnd = 5.dp,
                bottomStart = 5.dp,
                bottomEnd = 5.dp
            ),
            border = BorderStroke(1.dp, if (enabled) Color.Blue else Color.Gray),
            enabled = enabled,
            modifier = modifier,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Blue),
        ) {
            Text(text = text)
        }
}

@Composable
fun NoteRow(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClicked: (Note) -> Unit,
    onRemoveNoteClick: (Note) -> Unit,
) {
    Surface(
        modifier
            .padding(4.dp)
            .clip(
                RoundedCornerShape(
                    topEnd = 33.dp,
                    bottomStart = 33.dp,
                    topStart = 5.dp,
                    bottomEnd = 5.dp
                )
            )
            .fillMaxWidth(),
        color = Color.White,
        elevation = 6.dp,
        border = BorderStroke(1.dp, colorResource(id = R.color.orange)),
        shape = RoundedCornerShape(topEnd = 33.dp, bottomStart = 33.dp, topStart = 5.dp, bottomEnd = 5.dp),
    ) {
        // clickable 뒤에 Padding 을 넣어야 Click 영역 커짐
        Row(
            modifier
                .clickable { onNoteClicked(note) }
                .padding(horizontal = 14.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = note.title, style = MaterialTheme.typography.subtitle2)
                Text(text = note.description, style = MaterialTheme.typography.subtitle1)
                Text(
                    text = timeFormatter().print(note.entryDate),
                    style = MaterialTheme.typography.caption
                )
            }

            CustomButton(text = "삭제", onClick = {
                onRemoveNoteClick(note)
            })
        }
    }
}

@Composable
fun NoteAddButton(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        ExtendedFloatingActionButton(
            modifier = Modifier
                .padding(all = 16.dp)
                .align(alignment = Alignment.BottomEnd),
            backgroundColor = colorResource(id = R.color.orange),
            onClick = {

            },
            text = { Text(text = "Add") },
            icon = { Icon(imageVector = Icons.Filled.Add, contentDescription = "Add") },
        )
    }
}