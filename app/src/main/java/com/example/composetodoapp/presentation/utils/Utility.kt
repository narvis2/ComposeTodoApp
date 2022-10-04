package com.example.composetodoapp.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.io.ByteArrayOutputStream


fun timeFormatter(
    pattern: String = "yyyy-MM-dd HH:mm:ss"
): DateTimeFormatter = DateTimeFormat.forPattern(pattern)
    .withZone(DateTimeZone.forID("Asia/Seoul"))

fun Modifier.addFocusCleaner(focusManager: FocusManager, onClear: () -> Unit = {} ): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            onClear()
            focusManager.clearFocus()
        })
    }
}

fun Uri.toBitMap(context: Context): Bitmap {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) { // 28
        ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, this))
    } else {
        MediaStore.Images.Media.getBitmap(context.contentResolver, this)
    }
}

fun Bitmap.toByteArray(): ByteArray{
    val outputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    return outputStream.toByteArray()
}