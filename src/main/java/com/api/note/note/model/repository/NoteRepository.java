package com.api.note.note.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.note.note.model.entities.Note;

public interface NoteRepository extends JpaRepository<Note,Long> {
    @Query("SELECT n FROM Note n WHERE n.title LIKE %:title%")
    List<Note> findNoteByTitle(@Param("title") String title);
    
    List<Note> findNoteByUserId(long id);
}   
