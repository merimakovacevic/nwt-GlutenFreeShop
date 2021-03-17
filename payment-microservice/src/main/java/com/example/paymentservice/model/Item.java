package com.example.paymentservice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="item")
public class Item {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Product product;

    private int amount;

    public Item() {

    }

    public Item(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }
}
