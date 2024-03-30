package com.example.springrestdemo.exceptions;

// Клас виключення для ситуації, коли вже існує користувач з email із запиту
public class EmailAlreadyTakenException extends RuntimeException {
    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}
