package com.example.composetodoapp.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
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
import androidx.navigation.NavController
import com.example.composetodoapp.R
import com.example.composetodoapp.domain.model.Note
import com.example.composetodoapp.presentation.components.*
import com.example.composetodoapp.presentation.navigation.NavigationType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun NoteScreen(
    navController: NavController,
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit,
    onRemoveAll: () -> Unit,
    coroutineScope: CoroutineScope,
    setCurrentNote: (Note) -> Unit
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

    val showAllRemoveDialog = remember {
        mutableStateOf(false)
    }

    val scaffoldState = rememberScaffoldState()

    if (showDialog.value.first) {
        showDialog.value.second?.let { note ->
            CustomDialog(value = stringResource(id = R.string.dialog_title, note.title),
                setShowDialog = {
                    showDialog.value = it to null
                },
                onConfirmClick = {
                    onRemoveNote(note)
                    showDialog.value = false to null
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("${note.title}를 삭제하였습니다.")
                    }
                })
        }
    }

    if (showAllRemoveDialog.value) {
        CustomDialog(value = stringResource(id = R.string.dialog_all_remove_title),
            setShowDialog = {
                showAllRemoveDialog.value = it
            }) {
            onRemoveAll()
            showAllRemoveDialog.value = false
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar("전체 메모를 삭제하였습니다.")
            }
        }
    }

    // 현재 소프트웨어 키보드를 제어할 수 있는 SoftwareKeyboardController 를 반환
    val keyboardContainer = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.app_name))
            }, actions = {
                Icon(imageVector = Icons.Rounded.Notifications, contentDescription = "Icon")
            }, backgroundColor = Color(0xFFDADFE3)
            )
        }, scaffoldState = scaffoldState
    ) {

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

            CustomButton(
                text = "저장",
                enabled = title.value.isNotEmpty() && description.value.isNotEmpty()
            ) {
                if (title.value.isNotEmpty() && description.value.isNotEmpty()) {
                    onAddNote(Note(title = title.value, description = description.value))
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("메모를 작성하였습니다.")
                    }
                    title.value = ""
                    description.value = ""
                    keyboardContainer?.hide()
                }
            }

            // 구분선
            Divider(modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.note_cnt, notes.size),
                    modifier = Modifier.padding(end = 10.dp)
                )
                DeleteView(text = "전체 삭제", enabled = notes.isNotEmpty()) {
                    showAllRemoveDialog.value = true
                }
            }

            LazyColumn {
                items(notes) { note ->
                    NoteRow(
                        note = note,
                        onNoteClicked = {
                            setCurrentNote(it)
                            navController.navigate(route = NavigationType.DETAILSCREEN.name)
                        },
                        onRemoveNoteClick = {
                            showDialog.value = true to it
                        }
                    )
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun NotesScreenPreview() {

}