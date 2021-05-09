package com.example.orderservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderRatingDto {

    private Long userId;

    @Size(min = 1, message = "Rating items list must contain at least one element")
    private List<OrderRatingItem> ratingItems;
}
