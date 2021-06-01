package com.example.ratingmicroservice.controller.api;

import com.example.ratingmicroservice.dto.model.ReviewDto;
import com.example.ratingmicroservice.controller.response.RestResponse;
import com.example.ratingmicroservice.model.Review;
import com.example.ratingmicroservice.service.ReviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@Validated
@RefreshScope
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getReview(@Valid @RequestParam(value = "productId") @NotEmpty Long productId) throws JsonProcessingException {
        List<ReviewDto> reviews = reviewService.findAllByProductId(productId);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .result(reviews)
                .entity();
    }

    @SneakyThrows
    @PostMapping(value = "/add", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> addReview(@Valid @RequestBody ReviewDto reviewDto) throws JsonProcessingException {

        reviewService.addReview(reviewDto);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .message("Review successfully added.")
                .entity();
    }

    @DeleteMapping(value = "/delete", produces = "application/json")
    public ResponseEntity<?> deleteReview(@Valid @RequestParam @NotBlank Long reviewId) {

        reviewService.deleteReview(reviewId);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .message("Review successfully deleted.")
                .entity();
    }
}
