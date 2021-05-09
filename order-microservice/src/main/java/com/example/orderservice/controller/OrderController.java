package com.example.orderservice.controller;

import com.example.orderservice.controller.dto.OrderRatingDto;
import com.example.orderservice.controller.dto.OrderRatingItem;
import com.example.orderservice.grpc.GRPCClientService;
import com.example.orderservice.interfaces.RatingClient;
import com.example.orderservice.interfaces.dto.rating.RatingDto;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.ItemService;
import com.example.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;
    private final RatingClient ratingClient;
    private final GRPCClientService grpcClientService;

    public OrderController(OrderService orderService, ItemService itemService, RatingClient ratingClient, GRPCClientService grpcClientService) {
        this.orderService = orderService;
        this.itemService = itemService;
        this.ratingClient = ratingClient;
        this.grpcClientService = grpcClientService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/order/create", consumes = "application/json")
    public Order saveNewOrder(@Valid @RequestBody Order order) {
        itemService.saveItems(order.getItems());
        Order createdOrder = orderService.saveNewOrder(order);
        grpcClientService.sendSystemEvent("Order created", "INSERT", "Test User");
        return createdOrder;
    }

    @GetMapping(value = "/order/{id}")
    public Order getOrderById(@PathVariable Long id) {
        Order order = orderService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        grpcClientService.sendSystemEvent("Get order by id", "READ", "Test User");
        return order;
    }

    @GetMapping(value = "/order")
    public List<Order> getAllOrders() {
        List<Order> allOrders = orderService.findAll();
        String eventId = grpcClientService.sendSystemEvent("Get all orders", "READ", "Test User");
        System.out.println("Event ID returned by sytem-events: " + eventId);
        return allOrders;
    }

    @DeleteMapping(value = "/order/{id}")
    public void deleteOrderById(@PathVariable Long id) {
        orderService.deleteById(id);
        grpcClientService.sendSystemEvent("Order delete by id", "DELETE", "Test User");
    }

    @GetMapping(value = "/order/by-user/{userId}")
    public List<Order> getAllOrdersByUser(@PathVariable Long userId) {
        List<Order> allOrdersByUser = orderService.findAllOrdersByUser(userId);
        grpcClientService.sendSystemEvent("Get all orders by user", "READ", "Test User");
        return allOrdersByUser;
    }

    @PostMapping(value = "/order/rate")
    public void rateOrder(@Valid @RequestBody OrderRatingDto orderRatingDto) {
        for (OrderRatingItem ratingItem : orderRatingDto.getRatingItems()) {
            ratingClient.addRating(new RatingDto(ratingItem.getProductId(), orderRatingDto.getUserId(), ratingItem.getRating()));
        }
        grpcClientService.sendSystemEvent("Order rate", "INSERT", "Test User");
    }
}
