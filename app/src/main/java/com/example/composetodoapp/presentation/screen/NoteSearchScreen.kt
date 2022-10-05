package com.example.composetodoapp.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composetodoapp.R
import com.example.composetodoapp.domain.model.Note
import com.example.composetodoapp.presentation.components.NoteRow
import com.example.composetodoapp.presentation.components.NoteSearchBar
import com.example.composetodoapp.presentation.navigation.NavigationType

@Composable
fun NoteSearchScreen(
    navController: NavController,
    searchValue: String,
    searchNoteList: List<Note>,
    setSearchValue: (String) -> Unit,
    onSearchNoteList: (String) -> Unit,
    setCurrentNote: (Note) -> Unit,
    setCustomDialogTitle: (Pair<String, Int?>) -> Unit,
    setCustomDialogConfirmText: (Int) -> Unit,
    setCustomDialogCancelText: (Int) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Scaffold(topBar = {
        TopAppBar(
            backgroundColor = Color.White,
            title = { Text(text = stringResource(id = R.string.note_search)) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            })
    }) {
        Column(modifier = Modifier.fillMaxSize()) {
            NoteSearchBar(
                searchValue = searchValue,
                onChangeSearchValue = {
                    setSearchValue(it.take(10))
                    if (it.length > 10) {
                        focusManager.clearFocus()
                    }
                },
                onSubmitButton = {
                    onSearchNoteList(searchValue)
                    focusManager.clearFocus()
                },
                onSearchButtonClick = {
                    onSearchNoteList(searchValue)
                    setSearchValue("")
                    focusManager.clearFocus()
                },
                onResetSearchButton = { setSearchValue("") }
            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                color = colorResource(id = R.color.orange)
            )

            Text(
                text = stringResource(id = R.string.note_cnt, searchNoteList.size),
                modifier = Modifier.padding(start = 20.dp, bottom = 10.dp)
            )

            LazyColumn(modifier = Modifier.padding(horizontal = 5.dp)) {
                items(searchNoteList) { note ->
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

@Preview
@Composable
fun NoteSearchPreview() {

}