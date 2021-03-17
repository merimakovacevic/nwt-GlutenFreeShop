package com.example.ratingmicroservice.repository;

import com.example.ratingmicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
