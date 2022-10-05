package com.example.composetodoapp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.composetodoapp.data.database.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM `note` ORDER BY `note_entry_date` DESC")
    fun getNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM `note` WHERE `note_title` LIKE :searchQuery " + "OR `note_description` LIKE :searchQuery ORDER BY `note_entry_date` DESC")
    suspend fun getSearchNotes(searchQuery: String): List<NoteEntity>

    @Query("SELECT * FROM `note` WHERE `id`=:id")
    suspend fun getNoteById(id: String): NoteEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteEntity: NoteEntity)

    @Update
    suspend fun update(noteEntity: NoteEntity)

    @Query("DELETE FROM `note`")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)
}