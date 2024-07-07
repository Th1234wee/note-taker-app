package com.api.note.note.model.dto;

import com.api.note.note.model.entities.Note;
import com.api.note.utils.enums.NoteCategory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteDTO {
    private Long id;
    private String title;
    private String content;
    private String noteImageName;
    private boolean isImportant;
    private NoteCategory category;
    private Long userId;

      public NoteDTO(Note note) {
        this.id = note.getId();
        this.title = note.getTitle();
        this.content = note.getContent();
        this.noteImageName = note.getNoteImageName();
        this.isImportant = note.isImportant();
        this.category = note.getCategory();
        this.userId = note.getUser().getId(); 
    }
}