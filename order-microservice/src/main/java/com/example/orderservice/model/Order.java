package com.example.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_tabela", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id") })
public class Order {

    public enum OrderStatus {
        Pending, Created, Processing, Finished, Failed
    }

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User userId;

    private String deliveryAddress;
    //private Date timestamp = new Date();
    private OrderStatus orderStatus = OrderStatus.Pending;

    @OneToMany(mappedBy = "order")
    private List<Item> items;
}
