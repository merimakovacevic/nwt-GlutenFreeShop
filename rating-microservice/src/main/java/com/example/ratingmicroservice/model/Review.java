package com.example.ratingmicroservice.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="product_id")
    @NotNull(message = "Product id may not be empty.")
    private Product product;

    @ManyToOne
    @JoinColumn(name="user_id")
    @NotNull(message = "User id may not be empty.")
    private User user;

    @Size(min = 1, max = 300, message = "The comment should not be longer than 300 characters.")
    @NotNull(message = "Comment may not be empty.")
    private String comment;

    @NotNull(message = "Date may not be empty.")
    private Date date;

    public Review() { }

    public Review(Product product, User user, String comment, Date date) {
        this.product = product;
        this.user = user;
        this.comment = comment;
        this.date = date;
    }
}

