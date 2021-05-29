package com.example.ratingmicroservice.service;

import com.example.ratingmicroservice.controller.client.ProductClient;
import com.example.ratingmicroservice.dto.mapper.Mapper;
import com.example.ratingmicroservice.dto.model.ProductDto;
import com.example.ratingmicroservice.dto.model.ReviewDto;
import com.example.ratingmicroservice.exception.EntityType;
import com.example.ratingmicroservice.exception.RestResponseException;
import com.example.ratingmicroservice.grpc.GrpcClientService;
import com.example.ratingmicroservice.model.Product;
import com.example.ratingmicroservice.model.Review;
import com.example.ratingmicroservice.model.User;
import com.example.ratingmicroservice.repository.ReviewRepository;
import com.example.ratingmicroservice.repository.UserRepository;
import com.example.systemevents.SystemEventRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.ratingmicroservice.exception.EntityType.PRODUCT;
import static com.example.ratingmicroservice.exception.EntityType.USER;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductClient productClient;

    @Autowired
    GrpcClientService grpcClientService;

    public List<ReviewDto> findAllByProductId(Long productId) throws JsonProcessingException {
        if (!containsProduct(productClient.getAllProducts(), productId)) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.PRODUCT);
        }
        if (reviewRepository.findAllByProductId(productId).isEmpty()) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.REVIEW);
        }

        //TODO: STAVITI PRAVI USER ID UMJESTO NULE
        grpcClientService.sendSystemEvent(SystemEventRequest.LogType.INFO, "RatingMicroservice | Service: ReviewService | Method: findAllByProductId",
                0L, SystemEventRequest.Action.GET, "productId=" + productId);

        return reviewRepository.findAllByProductId(productId).stream().map(Mapper::toReviewDto).collect(Collectors.toList());
    }

    public ReviewDto addReview(ReviewDto reviewRequest) throws JsonProcessingException {
        validateProductAndUser(reviewRequest.getProductId(), reviewRequest.getUserId());

        grpcClientService.sendSystemEvent(SystemEventRequest.LogType.INFO, "RatingMicroservice | Service: ReviewService | Method: addReview",
                reviewRequest.getUserId(), SystemEventRequest.Action.POST, "");

        Review review = reviewRepository.save(new Review(new Product(reviewRequest.getProductId()), new User(reviewRequest.getUserId()),
                reviewRequest.getComment(), new Date()));
        return Mapper.toReviewDto(review);
    }

    public void deleteReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {

            //TODO: STAVITI PRAVI USER ID UMJESTO NULE
            grpcClientService.sendSystemEvent(SystemEventRequest.LogType.INFO, "RatingMicroservice | Service: ReviewService | Method: addReview",
                    0L, SystemEventRequest.Action.DELETE, "reviewId=" + reviewId);

            throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.REVIEW);
        }
        reviewRepository.deleteById(reviewId);
    }

    private boolean containsProduct(final List<ProductDto> list, final Long id){
        return list.stream().filter(o -> o.getId().equals(id)).findFirst().isPresent();
    }

    private void validateProductAndUser(Long productId, Long userId) throws RestResponseException {
        if (!containsProduct(productClient.getAllProducts(), productId)) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, PRODUCT);
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, USER);
        }
    }
}
