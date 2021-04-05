package com.example.ratingmicroservice.dto.model;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@ToString
@ApiModel
public class ReviewDto {
    Long id;

    @NotNull(message = "Field 'productId' cannot be empty.")
    private Long productId;

    @NotNull(message = "Field 'userId' cannot be empty.")
    private Long userId;

    @NotNull(message = "Field 'comment' cannot be empty.")
    @Size(min = 1, max = 300, message = "The comment should not be longer than 300 characters.")
    private String comment;

    private Date date;

    public ReviewDto() {
        this.date = new Date();
    }
}
