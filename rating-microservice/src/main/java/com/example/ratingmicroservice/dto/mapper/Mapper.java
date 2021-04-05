package com.example.ratingmicroservice.dto.mapper;

import com.example.ratingmicroservice.dto.model.RatingDto;
import com.example.ratingmicroservice.dto.model.ReviewDto;
import com.example.ratingmicroservice.model.Rating;
import com.example.ratingmicroservice.model.Review;

public class Mapper {
    public static RatingDto toRatingDto(Rating rating) {
        return new RatingDto()
                .setRate(rating.getRate())
                .setProductId(rating.getProduct().getId())
                .setUserId(rating.getUser().getId());
    }

    public static ReviewDto toReviewDto(Review review) {
        return new ReviewDto()
                .setProductId(review.getProduct().getId())
                .setUserId(review.getUser().getId())
                .setComment(review.getComment())
                .setDate(review.getDate())
                .setId(review.getId());
    }
}
