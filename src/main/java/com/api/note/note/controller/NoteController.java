package com.api.note.note.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.note.note.model.dto.NoteDTO;
import com.api.note.note.model.entities.Note;
import com.api.note.note.service.NoteService;
import com.api.note.user.model.entity.User;
import com.api.note.utils.enums.NoteCategory;
import com.api.note.utils.services.GetFullImageURL;
import com.api.note.utils.services.ImageHandler;

@RestController
@RequestMapping("/api/note")
public class NoteController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private GetFullImageURL getFullImageURL;
    @Autowired
    private ImageHandler imageHandler;

    @GetMapping("/getNotes")
    public ResponseEntity<List<NoteDTO>> getNotes(){
        try{
            List<NoteDTO> notes = noteService.getNotes();
            if(notes.isEmpty()){
                return new ResponseEntity<>(HttpStatusCode.valueOf(404));
            }
            for(NoteDTO note : notes){
                note.setNoteImageName(getFullImageURL.getURL(note.getNoteImageName(), "image"));
            }
            return new ResponseEntity<>(notes,HttpStatusCode.valueOf(200));
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }
    @GetMapping("/getNoteById/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable long id){
        try{
            Optional<Note> note = noteService.findNoteByID(id);
            if(note.isEmpty()){
                return new ResponseEntity<>(HttpStatusCode.valueOf(404));
            }
            Note foundNote = note.get();
            foundNote.setNoteImageName(getFullImageURL.getURL(foundNote.getNoteImageName(), "image"));
            return new ResponseEntity<>(foundNote,HttpStatusCode.valueOf(200));
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }
    @PostMapping("/createNote")
    public ResponseEntity<NoteDTO> createNote(
        @RequestParam("title") String title,
        @RequestParam("content") String content,
        @RequestParam("noteImageName") MultipartFile noteImageName,
        @RequestParam("isImportant") boolean isImportant,
        @RequestParam("category") NoteCategory category,
        @RequestParam("userId") long userId
    ){
        try{
            String noteImage = imageHandler.ImageProcessHandler(noteImageName);
            
            Note note = new Note();
            note.setTitle(title);
            note.setContent(content);
            note.setNoteImageName(noteImage);
            note.setImportant(isImportant); 
            note.setCategory(category);
            User user = new User();
            user.setId(userId);
            note.setUser(user);
            Note createdNote = noteService.createNote(note);
            NoteDTO reponseDTO = new NoteDTO(createdNote);
            return ResponseEntity.ok(reponseDTO );
            
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<Note>> getNotesByTitle(
        @RequestParam("title") String title
    ){
        try{
            List<Note> notes = noteService.findNoteByTitle(title);
            return ResponseEntity.ok(notes);
        }catch (Exception e){   
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }
}
