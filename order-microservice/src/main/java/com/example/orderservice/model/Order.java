package com.example.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_tabela")
public class Order {

    public enum OrderStatus {
        Pending, Processed, Finished, Failed
    }

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @Length(min = 5, message = "Delivery address length must be at least 5 characters.")
    private String deliveryAddress;

    private OrderStatus orderStatus = OrderStatus.Pending;

    @OneToMany(mappedBy = "order")
    private List<Item> items;

    public Order(User user, String deliveryAddress) {
        this.user = user;
        this.deliveryAddress = deliveryAddress;
    }
}
