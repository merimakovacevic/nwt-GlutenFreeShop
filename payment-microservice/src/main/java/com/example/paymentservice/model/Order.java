package com.example.paymentservice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private List<Item> items;

    public Order() {

    }

    public Order(List<Item> items) {
        this.items = items;
    }
}
