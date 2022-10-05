package com.example.composetodoapp.domain.usecase

import com.example.composetodoapp.domain.model.Note
import com.example.composetodoapp.domain.repository.NoteRepository
import javax.inject.Inject

class GetSearchNoteListUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(searchQuery: String): List<Note> {
        return noteRepository.getSearchNoteList(searchQuery)
    }
}