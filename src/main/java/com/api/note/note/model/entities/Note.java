package com.api.note.note.model.entities;

import java.time.LocalDateTime;
import org.springframework.data.annotation.LastModifiedDate;

import com.api.note.user.model.entity.User;
import com.api.note.utils.enums.NoteCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "note")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    @NotBlank
    private String title;

    @Column(name = "content")
    @NotBlank
    private String content;

    @Column(name = "note_image")
    private String noteImageName;

    @Column(name = "is_important")
    private boolean isImportant = false;

    @Enumerated(EnumType.STRING)
    private NoteCategory category = NoteCategory.GENERAL;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "modified_at")
    @LastModifiedDate
    private LocalDateTime modifiedAt = LocalDateTime.now();
    
    public boolean isImportant(){
        return isImportant;
    }

    public void setImportant(boolean important){
        this.isImportant = important;
    }
}
