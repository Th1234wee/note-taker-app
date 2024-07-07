package com.api.note.user.controller;

import com.api.note.authentication.model.JWTResponse;
import com.api.note.user.model.dto.UserNoteDTO;
import com.api.note.user.model.entity.User;
import com.api.note.user.service.UserService;
import com.api.note.utils.services.GetFullImageURL;
import com.api.note.utils.services.ImageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final GetFullImageURL getFullImageURL;
    @Autowired
    private final ImageHandler imageHandler;

    public UserController(UserService userService, GetFullImageURL getFullImageURL, ImageHandler imageHandler) {
        this.userService = userService;
        this.getFullImageURL = getFullImageURL;
        this.imageHandler = imageHandler;
    }
    

    @GetMapping("/getUsers")
    public ResponseEntity<List<UserNoteDTO>> getUsers() {
        try {
            List<UserNoteDTO> users = userService.getUsers();
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatusCode.valueOf(404));
            }
            for (UserNoteDTO user : users) {
                user.setImageUrl(getFullImageURL.getURL(user.getImageUrl(), "image"));
            }
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }
    @GetMapping("/getUserByID/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        try {
            Optional<User> user = userService.getUserByID(id);
            if(user.isEmpty()){
                return new ResponseEntity<>(HttpStatusCode.valueOf(404));
            }
            User foundUser = user.get();
            foundUser.setImageUrl(getFullImageURL.getURL(foundUser.getImageUrl(),"image"));
            return new ResponseEntity<>(foundUser, HttpStatusCode.valueOf(200));
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }

    }
    @GetMapping("/getUserByEmail")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        try {
            System.out.println(email);
            Optional<User> foundUser = userService.findUserByEmail(email);
            System.out.println(foundUser);
             return foundUser.map(user -> ResponseEntity.ok().body(user))
                        .orElseGet(() -> ResponseEntity.notFound().build());   
        }
        catch (Exception e){
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/createUser")
    public ResponseEntity<JWTResponse> createUser(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("imageUrl") MultipartFile imageUrl
    ) {
        try {
            String imageName = imageHandler.ImageProcessHandler(imageUrl);
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setImageUrl(imageName);
            return ResponseEntity.ok(userService.createUser(user));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("editUser/{id}")
    public ResponseEntity<Object> editUser(
            @PathVariable long id,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam(value = "imageUrl" , required = false) MultipartFile imageUrl
    ) throws IOException {
        User updatedUser = new User();
        updatedUser.setUsername(username);
        updatedUser.setEmail(email);
        updatedUser.setPassword(password);

        if (imageUrl != null) {
            String imageName = imageHandler.ImageProcessHandler(imageUrl);
            updatedUser.setImageUrl(imageName);
        }
        else{
            Optional<User> user = userService.getUserByID(id);

            if(user.isEmpty()){
                return new ResponseEntity<>(HttpStatusCode.valueOf(404));
            }
            else{
                User foundUser = user.get();
                updatedUser.setImageUrl(foundUser.getImageUrl());
            }
        }
        User user = userService.editUser(id, updatedUser);
        user.setImageUrl(getFullImageURL.getURL(user.getImageUrl(), "image"));
        return new ResponseEntity<>(user,HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/removeUser/{id}")
    public ResponseEntity<Object> removeUser(@PathVariable long id) {
        try {
            Optional<User> user = userService.getUserByID(id);
            if(user.isEmpty()){
                return new ResponseEntity<>(HttpStatusCode.valueOf(404));
            }
            userService.removeUser(id);
            return new ResponseEntity<>("User Remove Successfully",HttpStatusCode.valueOf(200));
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }
    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(
        @RequestBody User user
    ) throws Exception {
        return ResponseEntity.ok(userService.login(user));
    }
}
