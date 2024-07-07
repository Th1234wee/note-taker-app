package com.api.note.user.model.dto;

import java.time.LocalDateTime;
import java.util.List;



import com.api.note.note.model.dto.NoteDTO;

import com.api.note.user.model.entity.User;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserNoteDTO {
    private Long id;
    private String username;
    private String email;
    private String imageUrl;
    private List<NoteDTO> notes;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public UserNoteDTO(User user, List<NoteDTO> notes) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.imageUrl = user.getImageUrl();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
        this.notes = notes;
    }

}
