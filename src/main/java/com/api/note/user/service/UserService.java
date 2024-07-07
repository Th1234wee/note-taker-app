package com.api.note.user.service;

import com.api.note.authentication.model.JWTResponse;
import com.api.note.configuration.JWTTokenUtils;
import com.api.note.note.model.dto.NoteDTO;
import com.api.note.note.model.entities.Note;
import com.api.note.note.model.repository.NoteRepository;
import com.api.note.user.model.dto.UserNoteDTO;
import com.api.note.user.model.entity.User;
import com.api.note.user.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTTokenUtils jwtTokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private NoteRepository noteRepository;  

    public List<UserNoteDTO> getUsers(){
        List<User> users =  userRepository.findAll();
        return users.stream()
                    .map(user -> {
                        List<Note> notes = noteRepository.findNoteByUserId(user.getId());
                        return new UserNoteDTO(user,maptoNoteDTO(notes));
                    })
                    .collect(Collectors.toList());
    }
    
    public JWTResponse createUser(User user){
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setImageUrl(user.getImageUrl());

        user = userRepository.save(newUser);

        String token = jwtTokenUtils.generateToken(newUser);

        return new JWTResponse(token);
    }
    public Optional<User> getUserByID(long id){
        return userRepository.findById(id);
    }
    public User editUser(long id,User updatedUser){
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setEmail(updatedUser.getEmail());
                    user.setPassword(updatedUser.getPassword());
                    user.setImageUrl(updatedUser.getImageUrl());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User Not Found"));
    }
    public void removeUser(long id){
        userRepository.deleteById(id);
    }
    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public Optional<User> findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public JWTResponse login(User user){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        User authenticatedUser = userRepository.findByUsername(user.getUsername()).orElseThrow();
        String token = jwtTokenUtils.generateToken(authenticatedUser);

        return new JWTResponse(token);
    }

    private List<NoteDTO> maptoNoteDTO(List<Note> notes){
        return notes.stream()
                    .map(NoteDTO::new)
                    .collect(Collectors.toList());
    }
}
