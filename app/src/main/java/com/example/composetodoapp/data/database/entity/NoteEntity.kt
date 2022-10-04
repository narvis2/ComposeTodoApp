package com.example.composetodoapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.composetodoapp.data.database.converter.DateTimeConverter
import org.joda.time.DateTime

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "note_title")
    val title: String,
    @ColumnInfo(name = "note_description")
    val description: String,
    @field:TypeConverters(DateTimeConverter::class)
    @ColumnInfo(name = "note_entry_date")
    val entryDate: DateTime = DateTime.now(),
    @field:TypeConverters(DateTimeConverter::class)
    @ColumnInfo(name = "note_update_date")
    val updateDate: DateTime? = null,
    @ColumnInfo(name = "note_image")
    val originalImage: ByteArray?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NoteEntity

        if (id != other.id) return false
        if (title != other.title) return false
        if (description != other.description) return false
        if (entryDate != other.entryDate) return false
        if (updateDate != other.updateDate) return false
        if (originalImage != null) {
            if (other.originalImage == null) return false
            if (!originalImage.contentEquals(other.originalImage)) return false
        } else if (other.originalImage != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + entryDate.hashCode()
        result = 31 * result + (updateDate?.hashCode() ?: 0)
        result = 31 * result + (originalImage?.contentHashCode() ?: 0)
        return result
    }
}
