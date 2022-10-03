package com.example.composetodoapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
    titleError: Boolean,
    descriptionError: Boolean,
    onChangeTitle: (String) -> Unit,
    onChangeDescription: (String) -> Unit,
    onTitleSubmit: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
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
            EditTitleInput(title = title, onModifyText = onChangeTitle, isError = titleError) {
                onTitleSubmit()
            }
        }

        // 작성, 수정 시간
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

        // Description
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, bottom = 40.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(
                    BorderStroke(
                        2.dp,
                        colorResource(id = if (descriptionError) R.color.red else R.color.orange)
                    ),
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

                EditDescriptionInput(
                    description = description,
                    onModifyText = onChangeDescription,
                    onSubmitButton = {
                        onTitleSubmit()
                    }
                )
            }
        }

        OutlinedButton(
            onClick = {},
            shape = RoundedCornerShape(15),
            border = BorderStroke(
                1.dp,
                /**
                 * TODO:: 조건문 변경 필요
                 * 조건
                 * 1. title, description 이 비어있지 않고,
                 * 2. 기존의 title 과 바뀐 title 이 서로 달라야함
                 * 3. 기존의 description 과 바뀐 description 이 서로 달라야함
                 */
                if (title.isNotEmpty() && description.isNotEmpty()) Color.Red else Color.Gray
            ),
            /**
             * TODO:: 조건문 변경 필요
             * 조건
             * 1. title, description 이 비어있지 않고,
             * 2. 기존의 title 과 바뀐 title 이 서로 달라야함
             * 3. 기존의 description 과 바뀐 description 이 서로 달라야함
             */
            enabled = title.isNotEmpty() && description.isNotEmpty(),
            modifier = modifier,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
        ) {
            Text(text = stringResource(id = R.string.str_save))
        }
    }
}

@Composable
fun EditDescriptionInput(
    modifier: Modifier = Modifier,
    description: String,
    onModifyText: (String) -> Unit,
    onSubmitButton: () -> Unit
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = description,
        onValueChange = onModifyText,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(text = description)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        textStyle = TextStyle(
            Color.DarkGray,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start
        ),
        keyboardActions = KeyboardActions(onDone = {
            onSubmitButton()
        })
    )
}

@Composable
fun EditTitleInput(
    modifier: Modifier = Modifier,
    title: String,
    isError: Boolean,
    onModifyText: (String) -> Unit,
    onSubmitButton: () -> Unit
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .border(
                BorderStroke(
                    width = 2.dp,
                    color = colorResource(if (!isError) R.color.orange else R.color.red)
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
            keyboardActions = KeyboardActions(onDone = {
                onSubmitButton()
            }),
    )
}

@Preview
@Composable
fun NoteDetailContentViewPreview() {
    NoteDetailContentView(
        title = "text",
        description = "description",
        insertDate = "2022 09/30 17:53",
        onChangeTitle = {},
        onChangeDescription = {},
        onTitleSubmit = {},
        titleError = false,
        descriptionError = false,
    )
}