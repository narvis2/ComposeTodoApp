package com.example.composetodoapp.presentation.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composetodoapp.R
import com.example.composetodoapp.domain.model.Note
import com.example.composetodoapp.presentation.components.NoteDetailContentView
import com.example.composetodoapp.presentation.utils.timeFormatter

@Composable
fun NoteDetailScreen(navController: NavController, note: Note?, onDeleteNote: (Note) -> Unit) {
    note?.let { data ->
        val title = data.title
        val description = data.description
        val insertDate = timeFormatter("YY-MM-dd HH:mm").print(data.entryDate)
        Scaffold(topBar = {
            TopAppBar(backgroundColor = Color.White, title = {
                Text(
                    text = stringResource(
                        id = R.string.note_detail_title, title
                    ), style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                )
            }, navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }, actions = {
                IconButton(onClick = {
                    onDeleteNote(data)
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Search",
                        modifier = Modifier.padding(end = 10.dp)
                    )
                }
            })
        }) {
            NoteDetailContentView(
                title = title,
                description = description,
                insertDate = insertDate
            )
        }
    }
}