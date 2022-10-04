package com.example.composetodoapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composetodoapp.R
import com.example.composetodoapp.domain.model.Note
import com.example.composetodoapp.presentation.navigation.NavigationType
import com.example.composetodoapp.presentation.utils.timeFormatter
import com.example.composetodoapp.presentation.utils.toImageBitmap

@Composable
fun CustomButton(
    modifier: Modifier = Modifier, text: String, enabled: Boolean = true, onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, if (enabled) Color.Red else Color.Gray),
        enabled = enabled,
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
    ) {
        Text(text = text)
    }
}

@Composable
fun DeleteView(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
        OutlinedButton(
            onClick = onClick,
            shape = RoundedCornerShape(
                topStart = 5.dp,
                topEnd = 5.dp,
                bottomStart = 5.dp,
                bottomEnd = 5.dp
            ),
            border = BorderStroke(1.dp, if (enabled) colorResource(R.color.orange) else Color.Gray),
            enabled = enabled,
            modifier = modifier,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = colorResource(id = R.color.orange)),
        ) {
            Text(text = text)
        }
}

@Composable
fun NoteRow(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClicked: (Note) -> Unit,
    onRemoveNoteClick: (Note) -> Unit,
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { onNoteClicked(note) },
        elevation = 6.dp,
        shape = RoundedCornerShape(corner = CornerSize(14.dp)),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Surface(
                modifier = modifier
                    .padding(12.dp)
                    .size(100.dp),
                shape = RectangleShape,
                elevation = 4.dp
            ) {
                if (note.image == null) {
                    Icon(imageVector = Icons.Default.AccountBox, contentDescription = "No Image")
                } else {
                    Image(bitmap = note.image.toImageBitmap().asImageBitmap(), contentDescription = "Note Image")
                }
            }

            Row(
                modifier = modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = modifier.padding(4.dp)) {
                    Text(
                        text = note.title,
                        style = MaterialTheme.typography.subtitle2,
                        modifier = modifier.padding(bottom = 10.dp)
                    )
                    Text(text = note.description, style = MaterialTheme.typography.subtitle1)
                    Text(
                        text = timeFormatter().print(note.entryDate),
                        style = MaterialTheme.typography.caption,
                        modifier = modifier.padding(top = 5.dp)
                    )
                }

                CustomButton(text = "삭제", onClick = {
                    onRemoveNoteClick(note)
                }, modifier = modifier.padding(end = 10.dp))
            }
        }
    }
}

@Composable
fun NoteAddButton(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        ExtendedFloatingActionButton(
            modifier = Modifier
                .padding(all = 16.dp)
                .align(alignment = Alignment.BottomEnd),
            backgroundColor = colorResource(id = R.color.orange),
            onClick = {
                navController.navigate(route = NavigationType.WRITE_SCREEN.name)
            },
            text = { Text(text = "Add") },
            icon = { Icon(imageVector = Icons.Filled.Add, contentDescription = "Add") },
        )
    }
}