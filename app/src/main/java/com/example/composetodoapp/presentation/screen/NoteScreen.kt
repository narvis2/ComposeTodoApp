package com.example.composetodoapp.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetodoapp.R
import com.example.composetodoapp.domain.model.Note
import com.example.composetodoapp.presentation.components.CustomDialog
import com.example.composetodoapp.presentation.components.NoteButton
import com.example.composetodoapp.presentation.components.NoteInputText
import com.example.composetodoapp.presentation.components.NoteRow

@ExperimentalComposeUiApi
@Composable
fun NoteScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit,
) {
    val title = remember {
        mutableStateOf("")
    }
    val description = remember {
        mutableStateOf("")
    }
    val showDialog = remember {
        mutableStateOf<Pair<Boolean, Note?>>(false to null)
    }

    if (showDialog.value.first) {
        showDialog.value.second?.let { note ->
            CustomDialog(
                value = stringResource(id = R.string.dialog_title, note.title),
                setShowDialog = {
                    showDialog.value = it to null
                },
                onConfirmClick = {
                    onRemoveNote(note)
                    showDialog.value = false to null
                }
            )
        }
    }

    // 현재 소프트웨어 키보드를 제어할 수 있는 SoftwareKeyboardController 를 반환
    val keyboardContainer = LocalSoftwareKeyboardController.current

    Column(modifier = Modifier.padding(6.dp)) {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.app_name))
        }, actions = {
            Icon(imageVector = Icons.Rounded.Notifications, contentDescription = "Icon")
        }, backgroundColor = Color(0xFFDADFE3)
        )

        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NoteInputText(
                modifier = Modifier.padding(top = 9.dp, bottom = 8.dp),
                text = title.value,
                label = "제목",
                onTextChange = {
                    if (it.all { char ->
                            /**
                             * char.isLetter() -> 이 문자가 문자인 경우에 true
                             * char.isWhitespace() -> 유니 코드 표준에 따라 문자가 공백인지 여부 공백이면 true
                             */
                            /**
                             * char.isLetter() -> 이 문자가 문자인 경우에 true
                             * char.isWhitespace() -> 유니 코드 표준에 따라 문자가 공백인지 여부 공백이면 true
                             */
                            char.isLetter() || char.isWhitespace()
                        }) title.value = it
                },
                keyboardContainer = keyboardContainer
            )
            NoteInputText(
                modifier = Modifier.padding(top = 9.dp, bottom = 8.dp),
                text = description.value,
                label = "설명",
                onTextChange = {
                    if (it.all { char ->
                            char.isLetter() || char.isWhitespace()
                        }) description.value = it
                },
                keyboardContainer = keyboardContainer
            )

            NoteButton(text = "저장") {
                if (title.value.isNotEmpty() && description.value.isNotEmpty()) {
                    onAddNote(Note(title = title.value, description = description.value))
                    title.value = ""
                    description.value = ""
                    keyboardContainer?.hide()
                }
            }
        }

        // 구분선
        Divider(modifier = Modifier.padding(10.dp))
        LazyColumn {
            items(notes) { note ->
                NoteRow(note = note, onNoteClicked = {
                    showDialog.value = true to it
                })
            }
        }
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun NotesScreenPreview() {

}