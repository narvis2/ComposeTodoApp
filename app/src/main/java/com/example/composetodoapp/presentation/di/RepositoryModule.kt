package com.example.composetodoapp.presentation.di

import com.example.composetodoapp.data.repository.NoteRepositoryImpl
import com.example.composetodoapp.data.repository.datasource.LocalDataSource
import com.example.composetodoapp.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNoteRepository(localDataSource: LocalDataSource): NoteRepository {
        return NoteRepositoryImpl(localDataSource)
    }
}