package com.example.composetodoapp.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.composetodoapp.data.database.NoteDatabase
import com.example.composetodoapp.data.database.dao.NoteDao
import com.example.composetodoapp.data.repository.datasource.LocalDataSource
import com.example.composetodoapp.data.repository.datasourceimpl.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(noteDao: NoteDao): LocalDataSource {
        return LocalDataSourceImpl(noteDao)
    }

    @Singleton
    @Provides
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao {
        return noteDatabase.noteDao()
    }

    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "note_db"
        ).fallbackToDestructiveMigration()
            .enableMultiInstanceInvalidation() // multiple process 대응
            .build()
    }
}