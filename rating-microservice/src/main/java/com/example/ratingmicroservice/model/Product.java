package com.example.ratingmicroservice.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    public Product() {}

    public Product(Long id) {
        this.id = id;
    }
}
