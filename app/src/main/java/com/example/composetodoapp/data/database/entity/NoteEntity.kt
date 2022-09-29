package com.example.composetodoapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.composetodoapp.data.database.converter.DateTimeConverter
import org.joda.time.DateTime
import java.util.UUID

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "note_title")
    val title: String,
    @ColumnInfo(name = "note_description")
    val description: String,
    @field:TypeConverters(DateTimeConverter::class)
    @ColumnInfo(name = "note_entry_date")
    val entryDate: DateTime = DateTime.now()
)
