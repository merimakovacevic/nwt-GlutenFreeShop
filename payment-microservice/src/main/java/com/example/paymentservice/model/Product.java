package com.example.paymentservice.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private double price;

    public Product() {

    }

    public Product(double price) {
        this.price = price;
    }
}
