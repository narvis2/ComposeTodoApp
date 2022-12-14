package com.example.composetodoapp.data.mapper

import com.example.composetodoapp.data.database.entity.NoteEntity
import com.example.composetodoapp.domain.model.Note

fun Note.toNoteEntity(): NoteEntity = NoteEntity(
    id = this.id,
    title = this.title,
    description = this.description,
    entryDate = this.entryDate,
    updateDate = this.updateDate,
    originalImage = image
)

fun NoteEntity.toNote(): Note = Note(
    id = this.id,
    title = this.title,
    description = this.description,
    entryDate = this.entryDate,
    updateDate = this.updateDate,
    image = this.originalImage
)

fun List<NoteEntity>.toNoteList(): List<Note> = map {
    it.toNote()
}

fun List<Note>.toNoteEntityList(): List<NoteEntity> = map {
    it.toNoteEntity()
}