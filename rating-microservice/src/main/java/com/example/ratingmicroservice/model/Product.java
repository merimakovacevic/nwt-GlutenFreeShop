package com.example.ratingmicroservice.model;

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
}
