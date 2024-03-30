package com.example.springrestdemo.exceptions;

// Клас виключення для ситуації, коли не вийшло знайти користувача
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
