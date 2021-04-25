package com.example.ratingmicroservice.controller.api;

import com.example.ratingmicroservice.controller.response.RestResponse;
import com.example.ratingmicroservice.dto.model.AverageRatingDto;
import com.example.ratingmicroservice.dto.model.RatingDto;
import com.example.ratingmicroservice.model.Product;
import com.example.ratingmicroservice.repository.ProductRepository;
import com.example.ratingmicroservice.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@Validated
@RefreshScope
public class ProductController {

    @Autowired
    private ProductRepository productRepository;


    @PostMapping(value = "/products", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestParam @NotNull(message = "Param productId cannot be empty.") Long productId) {
        Product product = new Product(productId);
        return productRepository.save(product);
    }
}
