package com.example.ratingmicroservice.repository;

import com.example.ratingmicroservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
