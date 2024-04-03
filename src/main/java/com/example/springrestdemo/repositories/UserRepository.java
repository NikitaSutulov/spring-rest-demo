package com.example.springrestdemo.repositories;

import com.example.springrestdemo.entities.ForumUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Репозиторій, який розширює інтерфейс JpaRepository і містить методи пошуку за різними атрибутами, а також save та delete
// Більше про різні репозиторії в Spring Data JPA: https://www.baeldung.com/spring-data-repositories
@Repository
public interface UserRepository extends JpaRepository<ForumUser, Long> {
    Optional<ForumUser> findByEmail(String email);
}
