package com.api.note.note.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.note.note.model.dto.NoteDTO;
import com.api.note.note.model.entities.Note;
import com.api.note.note.model.repository.NoteRepository;
import com.api.note.user.model.entity.User;
import com.api.note.user.service.UserService;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private UserService userService;

    public List<NoteDTO> getNotes(){
        List<Note> notes =  noteRepository.findAll();
        return notes.stream()
                    .map(NoteDTO::new)
                    .collect(Collectors.toList());
    }

    public Optional<Note> findNoteByID(long id){
        return noteRepository.findById(id);
    }

    public Note createNote(Note note){
        long userId = note.getUser().getId();
        Optional<User> userOptional = userService.getUserByID(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            Note newNote = new Note();
            newNote.setTitle(note.getTitle());
            newNote.setContent(note.getContent());
            newNote.setNoteImageName(note.getNoteImageName());
            newNote.setImportant(false);
            newNote.setCategory(note.getCategory());
            newNote.setUser(user);

            return noteRepository.save(newNote);
        }
        else{
            throw new RuntimeException("");
        }
    }

    public Note editNote(long id,Note updatedNote){
        return noteRepository.findById(id)
               .map(note -> {
                    note.setTitle(updatedNote.getTitle());
                    note.setContent(updatedNote.getContent());
                    note.setNoteImageName(updatedNote.getNoteImageName());
                    note.setImportant(updatedNote.isImportant());
                    note.setCategory(updatedNote.getCategory());
                    return noteRepository.save(note);
               })
               .orElseThrow(() -> new RuntimeException("Note Not Found"));     
    }
    public void removeNote(long id){
        noteRepository.deleteById(id);
    }

    public List<Note> findNoteByTitle(String title){
        return noteRepository.findNoteByTitle(title);
    }

    public List<Note> findNoteByUserId(long id){
        return noteRepository.findNoteByUserId(id);
    }
}
