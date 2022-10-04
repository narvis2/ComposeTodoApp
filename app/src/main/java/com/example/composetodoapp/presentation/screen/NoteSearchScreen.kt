package com.example.composetodoapp.presentation.screen

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.composetodoapp.R
import com.example.composetodoapp.presentation.components.NoteSearchBar

@Composable
fun NoteSearchScreen(navController: NavController) {
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
        NoteSearchBar()
    }
}

@Preview
@Composable
fun NoteSearchPreview() {

}