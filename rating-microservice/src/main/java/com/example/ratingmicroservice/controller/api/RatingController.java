package com.example.ratingmicroservice.controller.api;

import com.example.ratingmicroservice.dto.model.AverageRatingDto;
import com.example.ratingmicroservice.dto.model.RatingDto;
import com.example.ratingmicroservice.controller.response.RestResponse;
import com.example.ratingmicroservice.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.cloud.client.discovery.DiscoveryClient;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@Validated
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping(value = "/rating/get", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> getRating(
            @RequestParam @NotNull(message = "Param productId cannot be empty.") Long productId,
            @RequestParam @NotNull(message = "Param userId cannot be empty.") Long userId) {

        Optional<AverageRatingDto> rating = ratingService.getRatingOfProduct(productId, userId);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .result(rating.get())
                .entity();
    }

    @PostMapping(value = "/rating/add", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> addRating(@Valid @RequestBody RatingDto ratingDto) {

        ratingService.addRating(ratingDto);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .message("Rating successfully saved.")
                .entity();
    }

    @PutMapping(value = "/rating/update", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> updateRating(@Valid @RequestBody RatingDto ratingDto) {

        ratingService.updateRating(ratingDto);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .message("Rating successfully updated.")
                .entity();
    }

    @DeleteMapping(value = "/rating/delete", produces = "application/json")
    public ResponseEntity<?> deleteRating(
            @RequestParam @NotNull(message = "Param productId cannot be empty.")  Long productId,
            @RequestParam @NotNull(message = "Param userId cannot be empty.") Long userId) {

        ratingService.deleteRating(productId, userId);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .message("Rating successfully deleted.")
                .entity();
    }
}
