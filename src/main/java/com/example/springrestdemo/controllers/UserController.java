package com.example.springrestdemo.controllers;

import com.example.springrestdemo.dto.UserRequest;
import com.example.springrestdemo.entities.ForumUser;
import com.example.springrestdemo.exceptions.EmailAlreadyTakenException;
import com.example.springrestdemo.exceptions.ResourceNotFoundException;
import com.example.springrestdemo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CREATE
    @PostMapping("")
    public ResponseEntity<ForumUser> createUser(@RequestBody UserRequest request) {
        try {
            ForumUser createdUser = userService.createForumUser(request.getFirstName(), request.getLastName(), request.getEmail());
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (EmailAlreadyTakenException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // READ
    @GetMapping("")
    public ResponseEntity<List<ForumUser>> getAll() {
        return new ResponseEntity<>(userService.getForumUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForumUser> getUserById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(userService.getForumUserById(id), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/email")
    public ResponseEntity<ForumUser> getUserByEmail(@RequestParam String email) {
        try {
            return new ResponseEntity<>(userService.getForumUserByEmail(email), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ForumUser> updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        try {
            ForumUser updatedUser = userService.updateForumUser(id, request.getFirstName(), request.getLastName(), request.getEmail());
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EmailAlreadyTakenException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ForumUser> deleteUserById(@PathVariable Long id) {
        try {
            ForumUser deletedUser = userService.deleteForumUserById(id);
            return new ResponseEntity<>(deletedUser, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/email")
    public ResponseEntity<ForumUser> deleteUserByEmail(@RequestParam String email) {
        try {
            ForumUser deletedUser = userService.deleteForumUserByEmail(email);
            return new ResponseEntity<>(deletedUser, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
