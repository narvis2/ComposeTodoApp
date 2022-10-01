package com.example.composetodoapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composetodoapp.R

@Composable
fun NoteDetailContentView(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    insertDate: String,
    onChangeTitle: (String) -> Unit,
    onChangeDescription: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = modifier
                .padding(top = 20.dp, bottom = 10.dp)
                .clip(
                    RoundedCornerShape(
                        10.dp
                    )
                )
                .fillMaxWidth(),
        ) {
            EditTitleInput(title = title) {
                onChangeTitle(it)
            }
        }

        Row(
            modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                modifier = modifier.padding(end = 5.dp),
                color = Color.White,
                border = BorderStroke(1.dp, colorResource(id = R.color.orange)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.note_create_at, insertDate),
                    style = TextStyle(color = Color.Gray, fontSize = 14.sp),
                    modifier = modifier.padding(10.dp)
                )
            }
            Surface(
                color = Color.White,
                border = BorderStroke(1.dp, colorResource(id = R.color.orange)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.note_update_at, insertDate),
                    style = TextStyle(color = Color.Gray, fontSize = 14.sp),
                    modifier = modifier.padding(10.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, bottom = 40.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(
                    BorderStroke(2.dp, colorResource(id = R.color.orange)),
                    shape = RoundedCornerShape(10.dp)
                )
                .background(color = Color.White), contentAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Text(
                    text = "내용",
                    style = TextStyle(Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp),
                    modifier = modifier.padding(start = 10.dp, bottom = 5.dp)
                )
                Divider(color = colorResource(id = R.color.orange))
                Text(
                    text = description, style = TextStyle(
                        Color.DarkGray, fontWeight = FontWeight.Medium, fontSize = 18.sp
                    ), modifier = modifier.padding(10.dp, top = 10.dp)
                )
            }
        }
    }
}

@Composable
fun EditTitleInput(
    modifier: Modifier = Modifier, title: String, onModifyText: (String) -> Unit
) {
    TextField(modifier = modifier
        .fillMaxWidth()
        .border(
            BorderStroke(
                width = 2.dp, color = colorResource(R.color.orange)
            ), shape = RoundedCornerShape(10.dp)
        ),
        textStyle = TextStyle(
            Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(text = title)
        },
        value = title,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        onValueChange = {
            onModifyText(it)
        },
    )
}

@Preview
@Composable
fun NoteDetailContentViewPreview() {
    NoteDetailContentView(title = "text",
        description = "description",
        insertDate = "2022 09/30 17:53",
        onChangeTitle = {},
        onChangeDescription = {})
}