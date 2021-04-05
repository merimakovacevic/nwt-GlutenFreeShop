package com.example.ratingmicroservice.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "rating")
@Accessors(chain = true)
public class Rating {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @NotNull(message = "Product id may not be empty.")
    private Product product;

    @ManyToOne
    @NotNull(message = "User id may not be empty.")
    private User user;

    @Min(value = 1, message = "Field 'rate' should have a value bigger than 1.")
    @Max(value = 5, message = "Field 'rate' should have a value less than 5.")
    @NotNull(message = "Field 'rate' may not be empty.")
    private Integer rate;

    public Rating(Product product, User user, Integer rate) {
        this.user = user;
        this.product = product;
        this.rate = rate;
    }

    public Rating() {

    }
}
