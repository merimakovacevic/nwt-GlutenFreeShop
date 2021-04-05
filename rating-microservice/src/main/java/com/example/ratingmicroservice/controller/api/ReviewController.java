package com.example.ratingmicroservice.controller.api;

import com.example.ratingmicroservice.dto.model.ReviewDto;
import com.example.ratingmicroservice.controller.response.RestResponse;
import com.example.ratingmicroservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping(value = "/review/get", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> getReview(@Valid @RequestParam(value = "productId") @NotEmpty Long productId) {

        List<ReviewDto> reviews = reviewService.findAllByProductId(productId);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .result(reviews)
                .entity();
    }

    @PostMapping(value = "/review/add", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> addReview(@Valid @RequestBody ReviewDto reviewDto) {

        reviewService.addReview(reviewDto);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .message("Review successfully added.")
                .entity();
    }

    @DeleteMapping(value = "/review/delete", produces = "application/json")
    public ResponseEntity<?> deleteReview(@Valid @RequestParam @NotBlank Long reviewId) {

        reviewService.deleteReview(reviewId);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .message("Review successfully deleted.")
                .entity();
    }
}
