package com.example.ratingmicroservice.service;

import com.example.ratingmicroservice.dto.mapper.Mapper;
import com.example.ratingmicroservice.dto.model.ProductDto;
import com.example.ratingmicroservice.dto.model.ReviewDto;
import com.example.ratingmicroservice.exception.EntityType;
import com.example.ratingmicroservice.exception.RestResponseException;
import com.example.ratingmicroservice.controller.client.ProductClient;
import com.example.ratingmicroservice.model.Product;
import com.example.ratingmicroservice.model.Review;
import com.example.ratingmicroservice.model.User;
import com.example.ratingmicroservice.repository.ProductRepository;
import com.example.ratingmicroservice.repository.ReviewRepository;
import com.example.ratingmicroservice.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductClient productClient;

    public List<ReviewDto> findAllByProductId(Long productId) throws JsonProcessingException {
        if (!containsProduct(productClient.getAllProducts(), productId)) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.PRODUCT);
        }
        if (reviewRepository.findAllByProductId(productId).isEmpty()) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.REVIEW);
        }
        return reviewRepository.findAllByProductId(productId).stream().map(Mapper::toReviewDto).collect(Collectors.toList());
    }

    public ReviewDto addReview(ReviewDto reviewRequest) throws JsonProcessingException {
        if (!containsProduct(productClient.getAllProducts(), reviewRequest.getProductId())) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.PRODUCT);
        }
        if (!userRepository.findById(reviewRequest.getUserId()).isPresent()) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.USER);
        }
        Product product = productRepository.save(new Product(reviewRequest.getProductId()));
        User user = userRepository.save(new User(reviewRequest.getUserId()));
        Review review = reviewRepository.save(new Review(product, user, reviewRequest.getComment(), new Date()));
        return Mapper.toReviewDto(review);
    }

    public void deleteReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.REVIEW);
        }
        reviewRepository.deleteById(reviewId);
    }

    private boolean containsProduct(final List<ProductDto> list, final Long id){
        return list.stream().filter(o -> o.getId().equals(id)).findFirst().isPresent();
    }
}
