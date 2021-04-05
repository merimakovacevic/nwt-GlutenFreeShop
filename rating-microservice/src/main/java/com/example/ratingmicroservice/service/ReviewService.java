package com.example.ratingmicroservice.service;

import com.example.ratingmicroservice.dto.mapper.Mapper;
import com.example.ratingmicroservice.dto.model.ReviewDto;
import com.example.ratingmicroservice.exception.EntityType;
import com.example.ratingmicroservice.exception.RestResponseException;
import com.example.ratingmicroservice.model.Product;
import com.example.ratingmicroservice.model.Review;
import com.example.ratingmicroservice.model.User;
import com.example.ratingmicroservice.repository.ProductRepository;
import com.example.ratingmicroservice.repository.ReviewRepository;
import com.example.ratingmicroservice.repository.UserRepository;
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

    public List<ReviewDto> findAllByProductId(Long productId) {
        if (!productRepository.findById(productId).isPresent()) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.PRODUCT);
        }
        if (reviewRepository.findAllByProductId(productId).isEmpty()) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.REVIEW);
        }
        return reviewRepository.findAllByProductId(productId).stream().map(Mapper::toReviewDto).collect(Collectors.toList());
    }

    public ReviewDto addReview(ReviewDto reviewRequest) {
        if (!productRepository.findById(reviewRequest.getProductId()).isPresent()) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.PRODUCT);
        }
        if (!userRepository.findById(reviewRequest.getUserId()).isPresent()) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.USER);
        }
        Product product = productRepository.getOne(reviewRequest.getProductId());
        User user = userRepository.getOne(reviewRequest.getUserId());
        Review review = reviewRepository.save(new Review(product, user, reviewRequest.getComment(), new Date()));
        return Mapper.toReviewDto(review);
    }

    public void deleteReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.REVIEW);
        }
        reviewRepository.deleteById(reviewId);
    }
}
