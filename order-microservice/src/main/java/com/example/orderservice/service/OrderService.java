package com.example.orderservice.service;

import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findAllOrdersByUser(Long id) {
        return orderRepository.findAllOrderByUserId(id);
    }

    public Order saveNewOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public void deleteById(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);

        Order order = optionalOrder.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        orderRepository.delete(order);
    }

    public void markOrderProcessed(Long orderId) {
        Order order = findById(orderId).orElseThrow(() -> new RuntimeException("Mark order processed - orderId does not exist: " + orderId));

        order.setOrderStatus(Order.OrderStatus.Processed);
        orderRepository.save(order);
    }

    public void markOrderOutOfStock(Long orderId, Set<Long> productIds) {
        Order order = findById(orderId).orElseThrow(() -> new RuntimeException("Mark order out of stock - orderId does not exist: " + orderId));

        order.setOrderStatus(Order.OrderStatus.Failed);
        orderRepository.save(order);

        System.out.println("Order out of stock - product ids: " + productIds);
    }

    public void markOrderPaid(Long orderId) {
        Order order = findById(orderId).orElseThrow(() -> new RuntimeException("Mark order paid - orderId does not exist: " + orderId));

        order.setOrderStatus(Order.OrderStatus.Finished);
        orderRepository.save(order);
    }

    public void markOrderPaymentFailed(Long orderId) {
        Order order = findById(orderId).orElseThrow(() -> new RuntimeException("Mark order payment failed - orderId does not exist: " + orderId));

        order.setOrderStatus(Order.OrderStatus.Failed);
        orderRepository.save(order);
    }
}
