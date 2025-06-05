package com.example.ZENA.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ZENA.model.User;

public interface UserRepository extends JpaRepository<User, String> {

     Optional<User> findByEmail(String email);
}
