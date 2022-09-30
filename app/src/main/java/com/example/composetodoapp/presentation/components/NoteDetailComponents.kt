package com.example.composetodoapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NoteDetailContentView(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    insertDate: String,
) {
    Column(modifier = modifier.fillMaxHeight().fillMaxWidth().background(Color.Red)) {

    }
}

@Preview
@Composable
fun NoteDetailContentViewPreview() {
    NoteDetailContentView(title = "text", description = "description", insertDate = "2022 09/30 17:53")
}