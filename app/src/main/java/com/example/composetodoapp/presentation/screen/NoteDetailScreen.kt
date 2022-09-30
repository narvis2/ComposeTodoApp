package com.example.composetodoapp.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composetodoapp.domain.model.Note

@Composable
fun NoteDetailScreen(navController: NavController, note: Note?) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "title : ${note?.title}")
        Text(text = "description : ${note?.description}")
        Text(text = "insertDate : ${note?.entryDate}")

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text(text = "종료")
        }
    }
}