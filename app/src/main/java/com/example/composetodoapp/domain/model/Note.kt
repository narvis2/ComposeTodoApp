package com.example.composetodoapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.joda.time.DateTime

@Parcelize
data class Note(
    val id: Long = 0L,
    val title: String,
    val description: String,
    val entryDate: DateTime = DateTime.now(),
    val updateDate: DateTime? = null
): Parcelable
