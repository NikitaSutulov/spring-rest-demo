package com.example.springrestdemo.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "forum_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "first_name", nullable = false)
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
