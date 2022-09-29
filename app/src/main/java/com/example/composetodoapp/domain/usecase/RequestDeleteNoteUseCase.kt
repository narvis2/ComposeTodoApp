package com.example.composetodoapp.domain.usecase

import com.example.composetodoapp.domain.model.Note
import com.example.composetodoapp.domain.repository.NoteRepository
import javax.inject.Inject

class RequestDeleteNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
){
    suspend operator fun invoke(note: Note) {
        noteRepository.deleteNote(note)
    }
}