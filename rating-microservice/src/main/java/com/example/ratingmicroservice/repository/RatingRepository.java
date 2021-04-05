package com.example.ratingmicroservice.repository;

import com.example.ratingmicroservice.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findAllByProductId(Long productId);
    Optional<Rating> findRatingByProductIdAndUserId(Long productId, Long userId);
}