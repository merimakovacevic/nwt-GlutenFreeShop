package com.example.ratingmicroservice.controller.api;

import com.example.ratingmicroservice.controller.response.RestResponse;
import com.example.ratingmicroservice.dto.model.AverageRatingDto;
import com.example.ratingmicroservice.dto.model.RatingDto;
import com.example.ratingmicroservice.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@Validated
@RefreshScope
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    @GetMapping(value = "/rating/get", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> getRating(
            @RequestParam @NotNull(message = "Param productId cannot be empty.") Long productId,
            @RequestParam @NotNull(message = "Param userId cannot be empty.") Long userId) throws Exception {

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
            @RequestParam @NotNull(message = "Param productId cannot be empty.") Long productId,
            @RequestParam @NotNull(message = "Param userId cannot be empty.") Long userId) {

        ratingService.deleteRating(productId, userId);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .message("Rating successfully deleted.")
                .entity();
    }

    /**
     * message mi je dio iz konfiguracije koji se nalazi na gitu
     * Sa anotacijom @Value mogu da pokupim neki property iz konfiga (bilo da se nalazi u application.properties fajlu
     * ili na git repozitoriju (config serveru)
     **/
    @Value("${message}")
    private String message;

    @RequestMapping("/greeting")
    public String hello() {
        return message;
    }
}
