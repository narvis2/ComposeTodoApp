package com.example.composetodoapp.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetodoapp.domain.model.Note
import com.example.composetodoapp.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList = _noteList.asStateFlow()

    private val _currentNote = MutableStateFlow<Note?>(null)
    val currentNote = _currentNote.asStateFlow()

    init {
        requestGetAllNoteList()
    }

    private fun requestGetAllNoteList() = viewModelScope.launch {
        getAllNoteListUseCase().collect {
            if (it.isEmpty()) return@collect
            _noteList.value = it
        }
    }

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

    fun setCurrentNote(note: Note) {
        _currentNote.value = note
    }
}