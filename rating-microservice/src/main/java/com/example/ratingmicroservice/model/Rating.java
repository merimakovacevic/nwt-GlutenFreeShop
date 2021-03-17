package com.example.ratingmicroservice.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;

    @Min(value =1)
    @Max(value =5)
    @NotNull
    private int rate;

    public Rating(Product product, User user, int rate) {
        this.user = user;
        this.product = product;
        this.rate = rate;
    }

    public Rating() {

    }
}
