package com.example.composetodoapp.presentation.screen

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.composetodoapp.R
import com.example.composetodoapp.domain.model.Note
import com.example.composetodoapp.presentation.components.NoteDetailContentView
import com.example.composetodoapp.presentation.navigation.NavigationType
import com.example.composetodoapp.presentation.utils.timeFormatter
import com.example.composetodoapp.presentation.utils.toBitMap
import com.example.composetodoapp.presentation.utils.toByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import kotlin.math.max

@ExperimentalComposeUiApi
@Composable
fun NoteDetailScreen(
    navController: NavController,
    note: Note?,
    scrollState: ScrollState,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    setCustomDialogTitle: (Pair<String, Int?>) -> Unit,
    setCustomDialogConfirmText: (Int) -> Unit,
    setCustomDialogCancelText: (Int) -> Unit,
    setCurrentNote: (Note) -> Unit,
) {
    note?.let { data ->
        val title = data.title
        val description = data.description
        val insertDate = timeFormatter("YY-MM-dd HH:mm").print(data.entryDate)
        val updateDate = if (data.updateDate != null) {
            timeFormatter("YY-MM-dd HH:mm").print(data.updateDate)
        } else {
            " - "
        }
        val image = data.image

        val modifyImage = remember {
            mutableStateOf(image)
        }

        val context = LocalContext.current

        val takePhotoFormAlbumLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    result.data?.data?.let {

                        val bitmap = it.toBitMap(context)
                        val ratio = if (max(bitmap.width.toDouble(), bitmap.height.toDouble()) > 1270) {
                            when {
                                bitmap.width > bitmap.height -> {
                                    1270.0 / bitmap.width.toDouble()
                                }
                                else -> {
                                    1270.0 / bitmap.height.toDouble()
                                }
                            }
                        } else {
                            1.0
                        }

                        val width = (bitmap.width.toDouble() * ratio).toInt()
                        val height = (bitmap.height.toDouble() * ratio).toInt()

                        Glide.with(context)
                            .asBitmap()
                            .load(bitmap)
                            .override(width, height)
                            .into(object : CustomTarget<Bitmap>() {
                                override fun onResourceReady(
                                    resource: Bitmap,
                                    transition: Transition<in Bitmap>?
                                ) {
                                    modifyImage.value = resource.toByteArray()

                                    if (bitmap != resource) {
                                        bitmap.recycle()
                                    }
                                }

                                override fun onLoadCleared(placeholder: Drawable?) {}
                            })
                    } ?: run {
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("이미지 가져오기에 실패하였습니다. 잠시 후 다시 시도해 주세요.")
                        }
                    }
                } else if (result.resultCode != Activity.RESULT_CANCELED) {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("알 수 없는 오류가 발생하였습니다. 잠시 후 다시 시도해 주세요.")
                    }
                }
            }

        val modifyTitle = remember {
            mutableStateOf(title)
        }
        val modifyDescription = remember {
            mutableStateOf(description)
        }

        val descriptionFocusRequester = remember {
            FocusRequester()
        }

        val isTitleError = modifyTitle.value.isEmpty()
        val isDescriptionError = modifyDescription.value.isEmpty()

        val focusManager = LocalFocusManager.current

        /**
         * Title 과 Description 의 내용이 비어있거나,
         * Title 과 Description 의 내용이 변경되지 않으면 -> false
         * Title 과 Description 의 내용이 비어있지 않고,
         * Title 과 Description 의 내용이 변경되었으면 -> true
         */
        val isSaveEnable =
            (modifyTitle.value.isNotEmpty() && modifyDescription.value.isNotEmpty()) && (title != modifyTitle.value || description != modifyDescription.value || !image.contentEquals(
                modifyImage.value
            ))

        Scaffold(topBar = {
            TopAppBar(backgroundColor = Color.White, title = {
                Text(
                    text = title, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
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
                                updateDate = DateTime.now(),
                                image = modifyImage.value
                            ))
                            setCustomDialogTitle(data.title to R.string.dialog_modify_title)
                            setCustomDialogConfirmText(R.string.str_modify)
                            setCustomDialogCancelText(R.string.str_cancel)
                            navController.navigate(route = NavigationType.CUSTOM_DIALOG.name)
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
                        navController.navigate(route = NavigationType.CUSTOM_DIALOG.name)
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
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NoteDetailContentView(
                    descriptionFocusRequester = descriptionFocusRequester,
                    title = modifyTitle.value,
                    description = modifyDescription.value,
                    insertDate = insertDate,
                    updateDate = updateDate,
                    titleError = isTitleError,
                    descriptionError = isDescriptionError,
                    modifyImageBitmap = modifyImage.value,
                    onChangeTitle = {
                        modifyTitle.value = it
                    },
                    onTitleSubmit = { descriptionFocusRequester.requestFocus() },
                    onChangeDescription = {
                        modifyDescription.value = it.take(250)
                        if (it.length > 250) {
                            focusManager.clearFocus()
                        }
                    },
                    onModifyImage = {
                        takePhotoFormAlbumLauncher.launch(
                            Intent(
                                Intent.ACTION_GET_CONTENT,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            ).apply {
                                type = "image/*"
                                action = Intent.ACTION_GET_CONTENT
                                putExtra(
                                    Intent.EXTRA_MIME_TYPES,
                                    arrayOf("image/jpeg", "image/png", "image/bmp", "image/webp")
                                )
                                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
                            }
                        )
                    },
                    onRemoveImage = {
                        modifyImage.value = null
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("이미지가 삭제되었습니다.")
                        }
                    }
                ) {
                    modifyDescription.value = ""
                }
            }
        }
    }
}