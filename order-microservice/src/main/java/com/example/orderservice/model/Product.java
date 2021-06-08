package com.example.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "price", nullable = false)
    private double price;

//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public Long getId() {
//        return this.id;
//    }
//
//    public double getPrice() {
//        return this.price;
//    }


    public Product(double price) {
        this.price = price;
    }
}