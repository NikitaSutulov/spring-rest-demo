package com.example.springrestdemo.entities;

import jakarta.persistence.*;
import lombok.*;

// Клас-сутність для користувача
// Більше про створення сутностей в Spring Data JPA: https://www.baeldung.com/jpa-entities
@Entity(name = "forum_user") // анотація для позначення сутності з можливістю вказання імені для сутності та таблиці
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumUser {
    @Id // анотація для первинного ключа (ідентифікатора)
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // генерація нових значень через SEQUENCE
    private Long id;

    @Column(name = "first_name", nullable = false) // анотація для атрибута (можна вказати свою назву, параметри nullable та unque)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    public ForumUser(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
