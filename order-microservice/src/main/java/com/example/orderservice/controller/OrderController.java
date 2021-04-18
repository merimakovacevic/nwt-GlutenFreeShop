package com.example.orderservice.controller;

import com.example.orderservice.model.Order;
import com.example.orderservice.service.ItemService;
import com.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    ItemService itemService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/order/create", consumes = "application/json")
    public Order saveNewOrder(@Valid @RequestBody Order order) {
        itemService.saveItems(order.getItems());
        return orderService.saveNewOrder(order);
    }

    @GetMapping(value = "/order/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    @GetMapping(value = "/order")
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @DeleteMapping(value = "/order/{id}")
    public void deleteOrderById(@PathVariable Long id) {
        orderService.deleteById(id);
    }

    @GetMapping(value = "/order/by-user/{userId}")
    public List<Order> getAllOrdersByUser(@PathVariable Long userId) {
        return orderService.findAllOrdersByUser(userId);
    }
}
