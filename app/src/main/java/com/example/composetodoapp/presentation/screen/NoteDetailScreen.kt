package com.example.composetodoapp.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun NoteDetailScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxHeight()
    ) {

        Text(text = "test")
        Text(text = "description")
        Text(text = "test22344")
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "종료")
        }
    }
}