package com.example.composetodoapp.domain.repository

import com.example.composetodoapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNoteList(): Flow<List<Note>>

    suspend fun getSearchNoteList(searchQuery: String): List<Note>

    suspend fun getNoteId(id: String): Note

    suspend fun saveNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun deleteAllNoteList()

    suspend fun deleteNote(note: Note)
}