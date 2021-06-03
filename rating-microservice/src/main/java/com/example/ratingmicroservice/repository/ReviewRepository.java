package com.example.ratingmicroservice.repository;

import com.example.ratingmicroservice.model.Product;
import com.example.ratingmicroservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProductId(Long productId);

    @Query(value = "select comment from Review where product = ?1")
    List<String> getCommentsForProduct(Product product);
}
