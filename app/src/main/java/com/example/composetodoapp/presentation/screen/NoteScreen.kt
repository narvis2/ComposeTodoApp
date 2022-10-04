package com.example.composetodoapp.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composetodoapp.R
import com.example.composetodoapp.domain.model.Note
import com.example.composetodoapp.presentation.components.*
import com.example.composetodoapp.presentation.navigation.NavigationType

@ExperimentalComposeUiApi
@Composable
fun NoteScreen(
    navController: NavController,
    notes: List<Note>,
    setCurrentNote: (Note) -> Unit,
    scaffoldState: ScaffoldState,
    setCustomDialogTitle: (Pair<String, Int?>) -> Unit,
    setCustomDialogConfirmText: (Int) -> Unit,
    setCustomDialogCancelText: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                actions = {
                    IconButton(
                        onClick = { navController.navigate(route = NavigationType.SEARCH_SCREEN.name) }
                    ) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    }
                },
                backgroundColor = Color.White
            )
        },
        scaffoldState = scaffoldState,
        floatingActionButton = {
            NoteAddButton(navController = navController)
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                    setCustomDialogTitle("" to R.string.dialog_all_remove_title)
                    setCustomDialogConfirmText(R.string.str_delete)
                    setCustomDialogCancelText(R.string.str_cancel)
                    navController.navigate(route = NavigationType.CUSTOM_DIALOG.name)
                }
            }

            LazyColumn {
                items(notes) { note ->
                    NoteRow(
                        note = note,
                        onNoteClicked = {
                            setCurrentNote(it)
                            navController.navigate(route = NavigationType.DETAIL_SCREEN.name)
                        },
                        onRemoveNoteClick = {
                            setCurrentNote(it)
                            setCustomDialogTitle(it.title to R.string.dialog_title)
                            setCustomDialogConfirmText(R.string.str_delete)
                            setCustomDialogCancelText(R.string.str_cancel)
                            navController.navigate(route = NavigationType.CUSTOM_DIALOG.name)
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