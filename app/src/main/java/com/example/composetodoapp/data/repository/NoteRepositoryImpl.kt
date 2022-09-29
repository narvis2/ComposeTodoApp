package com.example.composetodoapp.data.repository

import com.example.composetodoapp.data.mapper.toNote
import com.example.composetodoapp.data.mapper.toNoteEntity
import com.example.composetodoapp.data.mapper.toNoteList
import com.example.composetodoapp.data.repository.datasource.LocalDataSource
import com.example.composetodoapp.domain.model.Note
import com.example.composetodoapp.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : NoteRepository {
    override fun getAllNoteList(): Flow<List<Note>> {
        return localDataSource.getAllNotes().map {
            it.toNoteList()
        }.flowOn(Dispatchers.IO).distinctUntilChanged()
    }

    override suspend fun getNoteId(id: String): Note {
        return localDataSource.getNoteById(id).toNote()
    }

    override suspend fun saveNote(note: Note) {
        localDataSource.saveNote(note.toNoteEntity())
    }

    override suspend fun updateNote(note: Note) {
        localDataSource.updateNote(note.toNoteEntity())
    }

    override suspend fun deleteAllNoteList() {
        localDataSource.deleteAllNotes()
    }

    override suspend fun deleteNote(note: Note) {
        localDataSource.deleteNote(note.toNoteEntity())
    }
}