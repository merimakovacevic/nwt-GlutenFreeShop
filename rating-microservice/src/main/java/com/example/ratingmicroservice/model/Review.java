package com.example.ratingmicroservice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;

    private String comment;
    private Date date;

    public Review() {

    }

    public Review(Product product, User user, String comment, Date date) {
        this.product = product;
        this.user = user;
        this.comment = comment;
        this.date = date;
    }
}

