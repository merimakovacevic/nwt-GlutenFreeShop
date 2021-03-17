package com.example.orderservice.controller;

import com.example.orderservice.model.Order;
import com.example.orderservice.service.ItemService;
import com.example.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    OrderService orderService;
    ItemService itemService;

    @GetMapping(value = "/order/{userId}")
    public Order getOrderInfoByUser(@PathVariable Long userId) {
        return orderService.getOrderInfoByUser(userId);
    }

    @PostMapping(value = "/order/create", consumes = "application/json")
    public void saveNewOrder(@RequestBody Order order) {
        itemService.saveItems(order.getItems());
        orderService.saveNewOrder(order);
    }
}
