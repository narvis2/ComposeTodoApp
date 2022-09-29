package com.example.composetodoapp.domain.usecase

import com.example.composetodoapp.domain.repository.NoteRepository
import javax.inject.Inject

class RequestDeleteAllNoteListUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke() {
        noteRepository.deleteAllNoteList()
    }
}