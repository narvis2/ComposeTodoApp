package com.example.composetodoapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.composetodoapp.model.Note

class NoteViewModel : ViewModel() {
    private var noteList = mutableStateListOf<Note>()

    fun addNote(note: Note) {
        noteList.add(note)
    }

    fun removeNote(note: Note) {
        noteList.remove(note)
    }

    fun getAllNotes(): List<Note> {
        return noteList
    }
}