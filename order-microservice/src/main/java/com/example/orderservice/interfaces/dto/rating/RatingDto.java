package com.example.orderservice.interfaces.dto.rating;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RatingDto {

    private Long productId;
    private Long userId;
    private Integer rate;
}
