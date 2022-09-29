package com.example.composetodoapp.domain.model

import org.joda.time.DateTime
import java.time.Instant
import java.util.*

data class Note(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String,
    val entryDate: DateTime = DateTime.now()
)
