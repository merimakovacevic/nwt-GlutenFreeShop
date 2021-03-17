package com.example.orderservice.service;

import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public Order getOrderInfoByUser(Long id) {
        return orderRepository.findOrderByUserId(id);
    }

    public Order saveNewOrder(Order order) {
        return orderRepository.save(order);
    }
}
