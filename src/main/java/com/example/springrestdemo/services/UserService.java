package com.example.springrestdemo.services;

import com.example.springrestdemo.entities.ForumUser;
import com.example.springrestdemo.exceptions.EmailAlreadyTakenException;
import com.example.springrestdemo.exceptions.ResourceNotFoundException;
import com.example.springrestdemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE
    @Transactional
    public ForumUser createForumUser(String firstName, String lastName, String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyTakenException("User with email " + email + " already exists.");
        }
        return userRepository.save(new ForumUser(firstName, lastName, email));
    }

    // READ
    public List<ForumUser> getForumUsers() {
        return userRepository.findAll();
    }

    public ForumUser getForumUserById(Long id) {
        Optional<ForumUser> forumUserOptional = userRepository.findById(id);
        if (forumUserOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with id " + id + " not found.");
        }
        return forumUserOptional.get();
    }

    public ForumUser getForumUserByEmail(String email) {
        Optional<ForumUser> forumUserOptional = userRepository.findByEmail(email);
        if (forumUserOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with email " + email + " not found.");
        }
        return forumUserOptional.get();
    }

    // UPDATE
    @Transactional
    public ForumUser updateForumUser(Long id, String firstName, String lastName, String email) {
        Optional<ForumUser> forumUserOptional = userRepository.findById(id);
        if (forumUserOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with id " + id + " not found.");
        }
        ForumUser forumUser = forumUserOptional.get();
        Optional<ForumUser> userWithTheSameEmailOptional = userRepository.findByEmail(email);
        if (userWithTheSameEmailOptional.isPresent() && !Objects.equals(userWithTheSameEmailOptional.get().getId(), forumUser.getId())) {
            throw new EmailAlreadyTakenException("The email " + email + " is already taken.");
        }
        forumUser.setFirstName(firstName);
        forumUser.setLastName(lastName);
        forumUser.setEmail(email);
        return userRepository.save(forumUser);
    }

    // DELETE
    @Transactional
    public ForumUser deleteForumUserById(Long id) {
        Optional<ForumUser> forumUserOptional = userRepository.findById(id);
        if (forumUserOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with id " + id + " not found.");
        }
        ForumUser forumUser = forumUserOptional.get();
        userRepository.delete(forumUser);
        return forumUser;
    }

    @Transactional
    public ForumUser deleteForumUserByEmail(String email) {
        Optional<ForumUser> forumUserOptional = userRepository.findByEmail(email);
        if (forumUserOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with email " + email + " not found.");
        }
        ForumUser forumUser = forumUserOptional.get();
        userRepository.delete(forumUser);
        return forumUser;
    }

}
