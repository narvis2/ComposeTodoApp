package com.example.composetodoapp.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.composetodoapp.presentation.components.NoteSearchBar

@Composable
fun NoteSearchScreen(
    navController: NavController,
    searchValue: String,
    setSearchValue: (String) -> Unit
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
                    // TODO: Room 에 검색
                    focusManager.clearFocus()
                },
                onSearchButtonClick = {
                    // TODO: Room 에 검색
                    setSearchValue("")
                    focusManager.clearFocus()
                },
                onResetSearchButton = { setSearchValue("") }
            )

            Divider(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                color = colorResource(id = R.color.orange)
            )
        }
    }
}

@Preview
@Composable
fun NoteSearchPreview() {

}