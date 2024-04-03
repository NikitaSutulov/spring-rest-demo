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

// Сервіс для реалізації бізнес-логіки запитів
// Більше про сервіси в Spring: https://medium.com/@alamk6796/why-to-use-service-layer-in-spring-mvc-5f4fc52643c0
@Service
public class UserService {
    // Залежність - репозиторій
    private final UserRepository userRepository;

    // Конструктор для ін'єкції залежності userRepository
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE

    // Створити користувача
    @Transactional // транзакційний метод: якщо під час виконання трапляється помилка, стан бази даних відкочується до моменту перед викликом метода
    public ForumUser createForumUser(String firstName, String lastName, String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyTakenException("User with email " + email + " already exists."); // якщо email вже зайнято, кинути EmailAlreadyTakenException
        }
        return userRepository.save(new ForumUser(firstName, lastName, email)); // збереження й повернення нового користувача
    }

    // READ

    // Отримати всіх користувачів
    public List<ForumUser> getForumUsers() {
        return userRepository.findAll(); // повернути всіх користувачів
    }

    // Отримати користувача за ID
    public ForumUser getForumUserById(Long id) {
        Optional<ForumUser> forumUserOptional = userRepository.findById(id);
        if (forumUserOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with id " + id + " not found."); // якщо користувача з таким ID не знайдено, кинути ResourceNotFoundException
        }
        return forumUserOptional.get(); // повернути отриманого користувача
    }

    // Отримати користувача за email
    public ForumUser getForumUserByEmail(String email) {
        Optional<ForumUser> forumUserOptional = userRepository.findByEmail(email);
        if (forumUserOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with email " + email + " not found."); // якщо користувача з таким email не знайдено, кинути ResourceNotFoundException
        }
        return forumUserOptional.get(); // повернути отриманого користувача
    }

    // UPDATE

    // Оновити дані користувача
    @Transactional
    public ForumUser updateForumUser(Long id, String firstName, String lastName, String email) {
        Optional<ForumUser> forumUserOptional = userRepository.findById(id);
        if (forumUserOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with id " + id + " not found."); // якщо користувача з таким id не знайдено, кинути ResourceNotFoundException
        }
        ForumUser forumUser = forumUserOptional.get();
        Optional<ForumUser> userWithTheSameEmailOptional = userRepository.findByEmail(email);
        if (userWithTheSameEmailOptional.isPresent() && !Objects.equals(userWithTheSameEmailOptional.get().getId(), forumUser.getId())) {
            throw new EmailAlreadyTakenException("The email " + email + " is already taken."); // якщо email вже зайнято, кинути EmailAlreadyTakenException
        }
        forumUser.setFirstName(firstName);
        forumUser.setLastName(lastName);
        forumUser.setEmail(email);
        return userRepository.save(forumUser); // збереження й повернення оновлених даних про користувача
    }

    // DELETE

    // Видалити користувача за ID
    @Transactional
    public ForumUser deleteForumUserById(Long id) {
        Optional<ForumUser> forumUserOptional = userRepository.findById(id);
        if (forumUserOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with id " + id + " not found."); // якщо користувача з таким id не знайдено, кинути ResourceNotFoundException
        }
        ForumUser forumUser = forumUserOptional.get();
        userRepository.delete(forumUser);
        return forumUser; // видалення та повернення видаленого користувача
    }

    // Видалити користувача за Email
    @Transactional
    public ForumUser deleteForumUserByEmail(String email) {
        Optional<ForumUser> forumUserOptional = userRepository.findByEmail(email);
        if (forumUserOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with email " + email + " not found."); // якщо користувача з таким email не знайдено, кинути ResourceNotFoundException
        }
        ForumUser forumUser = forumUserOptional.get();
        userRepository.delete(forumUser);
        return forumUser; // видалення та повернення видаленого користувача
    }

}
