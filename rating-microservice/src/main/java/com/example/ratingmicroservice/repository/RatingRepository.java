package com.example.ratingmicroservice.repository;

import com.example.ratingmicroservice.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findAllByProductId(Long productId);
    Optional<Rating> findRatingByProductIdAndUserId(Long productId, Long userId);

    @Query(value = "select avg(rate) averageRating, count(id) numberOfRatings\n" +
            "from ratingdb.rating\n" +
            "where product_id = :productId", nativeQuery = true)
    Map<String, Object> getAverageRatingForProduct(@Param("productId") Long productId);
}