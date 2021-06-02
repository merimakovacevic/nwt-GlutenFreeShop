package com.example.ratingmicroservice.dto.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductInfoSyncDto {
    private Double averageRating;
    private Long numberOfRatings;
    private List<String> comments;
}