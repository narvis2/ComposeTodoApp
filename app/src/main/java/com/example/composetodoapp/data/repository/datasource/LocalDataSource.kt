package com.example.composetodoapp.data.repository.datasource

import com.example.composetodoapp.data.database.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getAllNotes(): Flow<List<NoteEntity>>

    suspend fun getSearchNotes(searchQuery: String): List<NoteEntity>

    suspend fun getNoteById(id: String): NoteEntity

    suspend fun saveNote(noteEntity: NoteEntity)

    suspend fun updateNote(noteEntity: NoteEntity)

    suspend fun deleteAllNotes()

    suspend fun deleteNote(noteEntity: NoteEntity)
}