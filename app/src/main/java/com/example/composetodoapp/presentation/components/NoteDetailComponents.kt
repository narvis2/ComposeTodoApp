package com.example.composetodoapp.presentation.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
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
import com.example.composetodoapp.presentation.utils.toImageBitmap

@Composable
fun NoteDetailContentView(
    modifier: Modifier = Modifier,
    descriptionFocusRequester: FocusRequester,
    title: String,
    description: String,
    insertDate: String,
    updateDate: String,
    titleError: Boolean,
    descriptionError: Boolean,
    modifyImageBitmap: ByteArray?,
    onChangeTitle: (String) -> Unit,
    onChangeDescription: (String) -> Unit,
    onTitleSubmit: () -> Unit,
    onModifyImage: () -> Unit,
    onRemoveImage: () -> Unit,
    onResetDescription: () -> Unit
) {
    // Image
    if (modifyImageBitmap != null) {
        Surface(
            modifier = modifier
                .padding(top = 15.dp)
                .size(300.dp),
            shape = RectangleShape,
            elevation = 4.dp
        ) {
            Image(
                bitmap = modifyImageBitmap.toImageBitmap().asImageBitmap(),
                contentDescription = "Image",
            )
        }
    }

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
                text = stringResource(id = R.string.note_update_at, updateDate),
                style = TextStyle(color = Color.Gray, fontSize = 14.sp),
                modifier = modifier.padding(10.dp)
            )
        }
    }

    // Description
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
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
                focusRequester = descriptionFocusRequester
            )
        }

        if (!descriptionError) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                modifier = modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 60.dp, end = 10.dp)
                    .clickable { onResetDescription() },
                tint = colorResource(id = R.color.orange)
            )
        }
    }

    // 이미지 수정 및 삭제
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = modifier.wrapContentHeight().wrapContentWidth().clickable { onModifyImage() },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(imageVector = Icons.Default.Image, contentDescription = "Image Modify")
            Text(text = "이미지 수정", textAlign = TextAlign.Center)
        }

        Row(
            modifier = modifier.wrapContentHeight().wrapContentWidth().clickable { onRemoveImage() },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Image Delete")
            Text(text = "이미지 삭제", textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun EditDescriptionInput(
    modifier: Modifier = Modifier,
    description: String,
    focusRequester: FocusRequester,
    onModifyText: (String) -> Unit
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .focusRequester(focusRequester = focusRequester),
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
        )
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
fun NoteDetailContentViewPreview() {}