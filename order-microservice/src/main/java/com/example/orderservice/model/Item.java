package com.example.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Product product;

    private int amount;

    @JsonIgnore
    @ManyToOne
    private Order order;

    public Item(int amount) {
        this.amount = amount;
    }
}
