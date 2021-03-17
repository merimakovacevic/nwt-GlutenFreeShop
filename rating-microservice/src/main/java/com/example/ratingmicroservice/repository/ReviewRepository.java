package com.example.ratingmicroservice.repository;

import com.example.ratingmicroservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
