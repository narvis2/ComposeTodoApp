package com.example.composetodoapp.presentation.screen

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
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
import com.example.composetodoapp.presentation.components.NoteWriteContentView
import com.example.composetodoapp.presentation.utils.toBitMap
import com.example.composetodoapp.presentation.utils.toByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.max

@Composable
fun NoteWriteScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    onSaveNote: (Note) -> Unit,
) {
    val context = LocalContext.current

    val attachBitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }

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
                                attachBitmap.value = resource

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

    val title = remember {
        mutableStateOf("")
    }
    val description = remember {
        mutableStateOf("")
    }

    val isTitleEmpty = title.value.isEmpty()
    val isDescriptionEmpty = description.value.isEmpty()

    val descriptionFocusRequester = remember {
        FocusRequester()
    }

    val focusManager = LocalFocusManager.current

    Scaffold(topBar = {
        TopAppBar(
            backgroundColor = Color.White,
            title = {
                Text(
                    text = stringResource(id = R.string.note_write),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(
                    onClick = {
                        onSaveNote(
                            Note(
                                title = title.value,
                                description = description.value,
                                updateDate = null,
                                image = attachBitmap.value?.toByteArray()
                            )
                        )
                        navController.popBackStack()
                    },
                    enabled = !(isTitleEmpty || isDescriptionEmpty)
                ) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "생성",
                        modifier = Modifier.padding(end = 10.dp),
                    )
                }
            }
        )
    }) {
        NoteWriteContentView(
            descriptionFocusRequester = descriptionFocusRequester,
            focusManager = focusManager,
            title = title.value,
            description = description.value,
            isTitleEmpty = isTitleEmpty,
            isDescriptionEmpty = isDescriptionEmpty,
            attachBitmap = attachBitmap.value,
            onRemoveAttachBitmap = {
                attachBitmap.value = null
            },
            onResetDescription = {
                description.value = ""
            },
            onChangeTitle = {
                title.value = it
            },
            onChangeDescription = {
                description.value = it.take(250)
                if (it.length > 250) {
                    focusManager.clearFocus()
                }
            },
            onAddImageClick = {
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
            }
        )
    }
}