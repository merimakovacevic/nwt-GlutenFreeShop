package com.example.ratingmicroservice.dto.model;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@Builder
@ApiModel
//@JsonInclude(value = JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
public class RatingDto {
    @NotNull(message = "Field 'productId' cannot be empty.")
    private Long productId;

    @NotNull(message = "Field 'userId' cannot be empty.")
    private Long userId;

    @NotNull(message = "Field 'rate' cannot be empty.")
    @Min(value = 1, message = "Field 'rate' should have a value bigger than 1.")
    @Max(value = 5, message = "Field 'rate' should have a value less than 5.")
    private Integer rate;

    public RatingDto(Long productId, Long userId, Integer rate) {
        this.productId = productId;
        this.userId = userId;
        this.rate = rate;
    }
}
