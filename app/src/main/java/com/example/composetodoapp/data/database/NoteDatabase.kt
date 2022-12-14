package com.example.composetodoapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composetodoapp.data.database.dao.NoteDao
import com.example.composetodoapp.data.database.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}