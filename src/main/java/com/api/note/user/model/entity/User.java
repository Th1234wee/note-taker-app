package com.api.note.user.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.api.note.note.model.entities.Note;

import java.time.LocalDateTime;
import java.util.*;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    @NotBlank
    private String email;

    @Column(name = "password")
    @NotBlank
    private String password;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "modified_at")
    @LastModifiedDate
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Note> notes;

   
    @Override
    public java.util.Collection<? extends GrantedAuthority> getAuthorities(){
        return Collections.emptyList();
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }
}
