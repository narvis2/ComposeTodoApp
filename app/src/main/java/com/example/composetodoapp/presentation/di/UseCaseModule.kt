package com.example.composetodoapp.presentation.di

import com.example.composetodoapp.domain.repository.NoteRepository
import com.example.composetodoapp.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetAllNoteListUseCase(
        noteRepository: NoteRepository
    ): GetAllNoteListUseCase = GetAllNoteListUseCase(noteRepository)

    @Provides
    @ViewModelScoped
    fun provideGetSearchNoteListUseCase(
        noteRepository: NoteRepository
    ): GetSearchNoteListUseCase = GetSearchNoteListUseCase(noteRepository)

    @Provides
    @ViewModelScoped
    fun provideGetNoteIdUseCase(
        noteRepository: NoteRepository
    ): GetNoteIdUseCase = GetNoteIdUseCase(noteRepository)

    @Provides
    @ViewModelScoped
    fun provideRequestDeleteAllNoteListUseCase(
        noteRepository: NoteRepository
    ): RequestDeleteAllNoteListUseCase = RequestDeleteAllNoteListUseCase(noteRepository)

    @Provides
    @ViewModelScoped
    fun provideRequestDeleteNoteUseCase(
        noteRepository: NoteRepository
    ): RequestDeleteNoteUseCase = RequestDeleteNoteUseCase(noteRepository)

    @Provides
    @ViewModelScoped
    fun provideRequestSaveNoteUseCase(
        noteRepository: NoteRepository
    ): RequestSaveNoteUseCase = RequestSaveNoteUseCase(noteRepository)

    @Provides
    @ViewModelScoped
    fun provideRequestUpdateNoteUseCase(
        noteRepository: NoteRepository
    ): RequestUpdateNoteUseCase = RequestUpdateNoteUseCase(noteRepository)
}