package com.example.composetodoapp.data.repository.datasourceimpl

import com.example.composetodoapp.data.database.dao.NoteDao
import com.example.composetodoapp.data.database.entity.NoteEntity
import com.example.composetodoapp.data.repository.datasource.LocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val noteDao: NoteDao
) : LocalDataSource {

    override fun getAllNotes(): Flow<List<NoteEntity>> {
        return noteDao.getNotes()
    }

    override suspend fun getNoteById(id: String): NoteEntity {
        return noteDao.getNoteById(id)
    }

    override suspend fun saveNote(noteEntity: NoteEntity) {
        noteDao.insert(noteEntity)
    }

    override suspend fun updateNote(noteEntity: NoteEntity) {
        noteDao.update(noteEntity)
    }

    override suspend fun deleteAllNotes() {
        noteDao.deleteAll()
    }

    override suspend fun deleteNote(noteEntity: NoteEntity) {
        noteDao.deleteNote(noteEntity)
    }
}