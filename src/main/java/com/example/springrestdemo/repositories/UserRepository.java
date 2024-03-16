package com.example.springrestdemo.repositories;

import com.example.springrestdemo.entities.ForumUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ForumUser, Long> {
    Optional<ForumUser> findByEmail(String email);
}
