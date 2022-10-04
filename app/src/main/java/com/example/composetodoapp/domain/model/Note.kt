package com.example.composetodoapp.domain.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.joda.time.DateTime

@Parcelize
data class Note(
    val id: Long = 0L,
    val title: String,
    val description: String,
    val entryDate: DateTime = DateTime.now(),
    val updateDate: DateTime? = null,
    val image: ByteArray? = null
): Parcelable {
    fun toOriginalImageBitmap(): Bitmap? {
        val originalImage = this.image ?: return null
        return BitmapFactory.decodeByteArray(originalImage, 0, originalImage.size)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Note

        if (id != other.id) return false
        if (title != other.title) return false
        if (description != other.description) return false
        if (entryDate != other.entryDate) return false
        if (updateDate != other.updateDate) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + entryDate.hashCode()
        result = 31 * result + (updateDate?.hashCode() ?: 0)
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }
}
