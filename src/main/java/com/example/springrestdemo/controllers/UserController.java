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

// Контролер запитів на шлях /users
// Більше про контролери в Spring Boot: https://www.baeldung.com/spring-controller-vs-restcontroller
@RestController
@RequestMapping("/users")
public class UserController {
    // Залежність - сервіс
    private final UserService userService;

    // Конструктор для ін'єкції залежності userService
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CREATE

    // Створити користувача
    @PostMapping("") // POST /users
    public ResponseEntity<ForumUser> createUser(@RequestBody UserRequest request) {
        try {
            ForumUser createdUser = userService.createForumUser(request.getFirstName(), request.getLastName(), request.getEmail());
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED); // якщо все добре, повернути користувача і код 201
        } catch (EmailAlreadyTakenException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage()); // якщо email вже зайнято, повернути код 400
        }
    }

    // READ

    // Отримати всіх користувачів
    @GetMapping("") // GET /users
    public ResponseEntity<List<ForumUser>> getAll() {
        return new ResponseEntity<>(userService.getForumUsers(), HttpStatus.OK);
    }

    // Отримати користувача за ID
    @GetMapping("/{id}") // Наприклад, GET /users/1
    public ResponseEntity<ForumUser> getUserById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(userService.getForumUserById(id), HttpStatus.OK); // якщо все добре, повернути користувача та код 200
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage()); // якщо користувача з таким ID не знайдено, повернути код 404
        }
    }

    // Отримати користувача за ID
    @GetMapping("/email") // Наприклад, GET /users/email?email=nikita@gmail.com
    public ResponseEntity<ForumUser> getUserByEmail(@RequestParam String email) {
        try {
            return new ResponseEntity<>(userService.getForumUserByEmail(email), HttpStatus.OK); // якщо все добре, повернути користувача та код 200
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage()); // якщо користувача з таким email не знайдено, повернути код 404
        }
    }

    // UPDATE

    // Оновити дані користувача
    @PutMapping("/{id}") // Наприклад, PUT /users/1
    public ResponseEntity<ForumUser> updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        try {
            ForumUser updatedUser = userService.updateForumUser(id, request.getFirstName(), request.getLastName(), request.getEmail());
            return new ResponseEntity<>(updatedUser, HttpStatus.OK); // якщо все добре, повернути нові дані користувача та код 200
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage()); // якщо користувача з таким ID не знайдено, повернути код 404
        } catch (EmailAlreadyTakenException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage()); // якщо новий email вже зайнято, повернути код 400
        }
    }

    // DELETE

    // Видалити користувача за ID
    @DeleteMapping("/{id}") // Наприклад, DELETE /users/1
    public ResponseEntity<ForumUser> deleteUserById(@PathVariable Long id) {
        try {
            ForumUser deletedUser = userService.deleteForumUserById(id);
            return new ResponseEntity<>(deletedUser, HttpStatus.OK); // якщо все добре, повернути нові дані користувача та код 200
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage()); // якщо користувача з таким ID не знайдено, повернути код 404
        }
    }

    // Видалити користувача за email
    @DeleteMapping("/email") // Наприклад, DELETE users/email?email=nikita@gmail.com
    public ResponseEntity<ForumUser> deleteUserByEmail(@RequestParam String email) {
        try {
            ForumUser deletedUser = userService.deleteForumUserByEmail(email);
            return new ResponseEntity<>(deletedUser, HttpStatus.OK); // якщо все добре, повернути нові дані користувача та код 200
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage()); // якщо користувача з таким ID не знайдено, повернути код 404
        }
    }
}
