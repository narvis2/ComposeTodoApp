package com.example.composetodoapp.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetodoapp.domain.model.Note
import com.example.composetodoapp.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getAllNoteListUseCase: GetAllNoteListUseCase,
    private val getNoteIdUseCase: GetNoteIdUseCase,
    private val requestDeleteAllNoteListUseCase: RequestDeleteAllNoteListUseCase,
    private val requestDeleteNoteUseCase: RequestDeleteNoteUseCase,
    private val requestSaveNoteUseCase: RequestSaveNoteUseCase,
    private val requestUpdateNoteUseCase: RequestUpdateNoteUseCase
) : ViewModel() {

    private val _currentNote = MutableStateFlow<Note?>(null)
    val currentNote = _currentNote.asStateFlow()

    fun setCurrentNote(note: Note) {
        _currentNote.value = note
    }

    val requestGetAllNoteList =
        getAllNoteListUseCase().stateIn(initialValue = emptyList(), started = SharingStarted.WhileSubscribed(5000L), scope = viewModelScope)

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        requestSaveNoteUseCase(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        requestUpdateNoteUseCase(note)
    }

    fun removeNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        requestDeleteNoteUseCase(note)
    }

    fun removeAllNote() = viewModelScope.launch(Dispatchers.IO) {
        requestDeleteAllNoteListUseCase()
    }
}