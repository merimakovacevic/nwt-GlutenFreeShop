package com.example.ratingmicroservice.dto.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@ApiModel
public class AverageRatingDto {
    private Integer rateByUser;
    private Double averageRating;

    public AverageRatingDto(Integer rateByUser, Double averageRating) {
        this.rateByUser = rateByUser;
        this.averageRating = averageRating;
    }
}
