package com.example.composetodoapp.presentation.components

import android.graphics.Bitmap
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composetodoapp.R
import com.example.composetodoapp.presentation.utils.addFocusCleaner

@Composable
fun NoteWriteContentView(
    modifier: Modifier = Modifier,
    descriptionFocusRequester: FocusRequester,
    focusManager: FocusManager,
    title: String,
    description: String,
    isTitleEmpty: Boolean,
    isDescriptionEmpty: Boolean,
    attachBitmap: Bitmap?,
    onRemoveAttachBitmap: () -> Unit,
    onResetDescription: () -> Unit,
    onChangeTitle: (String) -> Unit,
    onChangeDescription: (String) -> Unit,
    onAddImageClick: () -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(20.dp)
            .addFocusCleaner(focusManager),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier
                .fillMaxWidth(),
        ) {
            TitleInput(
                title = title,
                isError = isTitleEmpty,
                onModifyText = onChangeTitle,
            ) {
                descriptionFocusRequester.requestFocus()
            }

            DescriptionInput(
                modifier = modifier.padding(top = 15.dp),
                description = description,
                isError = isDescriptionEmpty,
                onModifyText = onChangeDescription,
                focusRequester = descriptionFocusRequester,
                onResetDescription = onResetDescription
            )
        }

        attachBitmap?.let {
            BoxWithConstraints(modifier = Modifier.padding(top = 15.dp)) {
                Image(bitmap = it.asImageBitmap(), contentDescription = "")
                IconButton(onClick = onRemoveAttachBitmap, modifier = Modifier.align(Alignment.TopEnd).padding(top = 40.dp)) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "")
                }
            }
        }

        Row(
            modifier = modifier.fillMaxWidth().padding(bottom = if (attachBitmap == null) 20.dp else 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = modifier.clickable { onAddImageClick() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(imageVector = Icons.Default.Image, contentDescription = "이미지")
                Text(text = "이미지 첨부", textAlign = TextAlign.Center)
            }

            Text(
                text = stringResource(
                    id = R.string.note_write_description_length,
                    description.length
                ),
                modifier = modifier.padding(end = 10.dp)
            )
        }
    }
}

@Composable
fun TitleInput(
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
                    width = 1.dp,
                    color = colorResource(if (!isError) R.color.orange else R.color.red)
                ), shape = RoundedCornerShape(10.dp)
            ),
        textStyle = TextStyle(
            Color.Black, fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Start
        ),
        singleLine = true,
        label = {
            Text(
                text = stringResource(id = R.string.note_write_title_helper), style = TextStyle(
                    colorResource(id = if (!isError) R.color.orange else R.color.red)
                )
            )
        },
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

@Composable
fun DescriptionInput(
    modifier: Modifier = Modifier,
    description: String,
    isError: Boolean,
    focusRequester: FocusRequester,
    onResetDescription: () -> Unit,
    onModifyText: (String) -> Unit,
) {
    TextField(modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .border(
            BorderStroke(
                width = 1.dp, color = colorResource(if (!isError) R.color.orange else R.color.red)
            ), shape = RoundedCornerShape(10.dp)
        )
        .focusRequester(focusRequester = focusRequester),
        value = description,
        onValueChange = onModifyText,
        label = {
            Text(
                text = stringResource(id = R.string.note_write_description_helper),
                style = TextStyle(colorResource(id = if (!isError) R.color.orange else R.color.red))
            )
        },
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
        trailingIcon = {
            if (!isError) {
                IconButton(onClick = onResetDescription) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "",
                        tint = colorResource(R.color.orange),
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                    )
                }
            }
        },
    )
}