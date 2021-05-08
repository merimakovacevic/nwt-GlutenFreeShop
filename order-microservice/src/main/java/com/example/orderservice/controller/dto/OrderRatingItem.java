package com.example.orderservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
public class OrderRatingItem {
    private Long productId;

    @Min(1)
    @Max(5)
    private int rating;
}