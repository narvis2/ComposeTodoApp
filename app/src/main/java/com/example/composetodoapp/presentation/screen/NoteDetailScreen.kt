package com.example.composetodoapp.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composetodoapp.R
import com.example.composetodoapp.domain.model.Note
import com.example.composetodoapp.presentation.components.NoteDetailContentView
import com.example.composetodoapp.presentation.navigation.NavigationType
import com.example.composetodoapp.presentation.utils.timeFormatter
import org.joda.time.DateTime

@ExperimentalComposeUiApi
@Composable
fun NoteDetailScreen(
    navController: NavController,
    note: Note?,
    isTitleError: Boolean,
    isDescriptionError: Boolean,
    setCustomDialogTitle: (Pair<String, Int?>) -> Unit,
    setCustomDialogConfirmText: (Int) -> Unit,
    setCustomDialogCancelText: (Int) -> Unit,
    setCurrentNote: (Note) -> Unit,
    onSetTitleError: (Boolean) -> Unit,
    onSetDescriptionError: (Boolean) -> Unit
) {
    note?.let { data ->
        val keyboardContainer = LocalSoftwareKeyboardController.current

        val title = data.title
        val description = data.description
        val insertDate = timeFormatter("YY-MM-dd HH:mm").print(data.entryDate)
        val updateDate = if (data.updateDate != null) {
            timeFormatter("YY-MM-dd HH:mm").print(data.updateDate)
        } else {
            " - "
        }

        val modifyTitle = remember {
            mutableStateOf(title)
        }
        val modifyDescription = remember {
            mutableStateOf(description)
        }

        /**
         * Title 과 Description 의 내용이 비어있거나,
         * Title 과 Description 의 내용이 변경되지 않으면 -> false
         * Title 과 Description 의 내용이 비어있지 않고,
         * Title 과 Description 의 내용이 변경되었으면 -> true
         */
        val isSaveEnable =
            (modifyTitle.value.isNotEmpty() && modifyDescription.value.isNotEmpty()) && (title != modifyTitle.value || description != modifyDescription.value)

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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = {
                            setCurrentNote(Note(
                                id = data.id,
                                title = modifyTitle.value,
                                description = modifyDescription.value,
                                entryDate = data.entryDate,
                                updateDate = DateTime.now()
                            ))
                            setCustomDialogTitle(data.title to R.string.dialog_modify_title)
                            setCustomDialogConfirmText(R.string.str_modify)
                            setCustomDialogCancelText(R.string.str_cancel)
                            navController.navigate(route = NavigationType.CUSTOMDIALOG.name)
                        }, enabled = isSaveEnable
                    ) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = "Save",
                            modifier = Modifier.padding(end = 5.dp),
                            tint = if (isSaveEnable) colorResource(id = R.color.orange) else Color.DarkGray
                        )
                    }

                    IconButton(onClick = {
                        setCurrentNote(data)
                        setCustomDialogTitle(data.title to R.string.dialog_title)
                        setCustomDialogConfirmText(R.string.str_delete)
                        setCustomDialogCancelText(R.string.str_cancel)
                        navController.navigate(route = NavigationType.CUSTOMDIALOG.name)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.padding(end = 10.dp)
                        )
                    }
                }
            })
        }) {
            NoteDetailContentView(
                title = modifyTitle.value,
                description = modifyDescription.value,
                insertDate = insertDate,
                updateDate = updateDate,
                titleError = isTitleError,
                descriptionError = isDescriptionError,
                onChangeTitle = {
                    onSetTitleError(it.isEmpty())
                    modifyTitle.value = it
                },
                onChangeDescription = {
                    onSetDescriptionError(it.isEmpty())
                    modifyDescription.value = it
                },
            ) {
                keyboardContainer?.hide()
            }
        }
    }
}