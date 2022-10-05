package com.example.composetodoapp.presentation.ui

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetodoapp.domain.model.Note
import com.example.composetodoapp.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getAllNoteListUseCase: GetAllNoteListUseCase,
    private val getSearchNoteListUseCase: GetSearchNoteListUseCase,
    private val getNoteIdUseCase: GetNoteIdUseCase,
    private val requestDeleteAllNoteListUseCase: RequestDeleteAllNoteListUseCase,
    private val requestDeleteNoteUseCase: RequestDeleteNoteUseCase,
    private val requestSaveNoteUseCase: RequestSaveNoteUseCase,
    private val requestUpdateNoteUseCase: RequestUpdateNoteUseCase
) : ViewModel() {
    private val _searchValue = MutableStateFlow("")
    val searchValue = _searchValue.asStateFlow()

    fun setSearchValue(query: String) {
        _searchValue.value = query
    }

    private val _customDialogTitle = MutableStateFlow<Pair<String, Int?>>("" to null)
    val customDialogTitle = _customDialogTitle.asStateFlow()

    fun setCustomDialogTitle(dialogTitle: Pair<String, Int?>) {
        _customDialogTitle.value = dialogTitle
    }

    private val _customDialogConfirmText = MutableStateFlow(0)
    val customDialogConfirmText = _customDialogConfirmText.asStateFlow()

    fun setCustomDialogConfirmText(@StringRes confirmText: Int) {
        _customDialogConfirmText.value = confirmText
    }

    private val _customDialogCancelText = MutableStateFlow(0)
    val customDialogCancelText = _customDialogCancelText.asStateFlow()

    fun setCustomDialogCancelText(@StringRes cancelText: Int) {
        _customDialogCancelText.value = cancelText
    }

    private val _currentNote = MutableStateFlow<Note?>(null)
    val currentNote = _currentNote.asStateFlow()

    fun setCurrentNote(note: Note) {
        _currentNote.value = note
    }

    val requestGetAllNoteList = getAllNoteListUseCase().stateIn(
        initialValue = emptyList(),
        started = SharingStarted.WhileSubscribed(5000L),
        scope = viewModelScope
    )

    private val _searchNoteList = MutableStateFlow<List<Note>>(emptyList())
    val searchNoteList = _searchNoteList.asStateFlow()

    fun requestGetSearchNoteList(searchQuery: String) = viewModelScope.launch(Dispatchers.IO) {
        _searchNoteList.value = getSearchNoteListUseCase(searchQuery)
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
}