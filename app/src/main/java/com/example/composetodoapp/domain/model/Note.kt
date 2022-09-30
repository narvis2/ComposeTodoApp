package com.example.composetodoapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.joda.time.DateTime
import java.util.*

@Parcelize
data class Note(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String,
    val entryDate: DateTime = DateTime.now()
): Parcelable
