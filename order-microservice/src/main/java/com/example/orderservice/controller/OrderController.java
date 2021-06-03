package com.example.orderservice.controller;

import com.example.orderservice.amqp.OrderCreatedSender;
import com.example.orderservice.amqp.OrderPaymentSender;
import com.example.orderservice.amqp.event.OrderCreatedEvent;
import com.example.orderservice.amqp.event.OrderPaymentEvent;
import com.example.orderservice.controller.dto.OrderRatingDto;
import com.example.orderservice.controller.dto.OrderRatingItem;
import com.example.orderservice.interfaces.ProductClient;
import com.example.orderservice.interfaces.RatingClient;
import com.example.orderservice.interfaces.dto.product.CalculatePriceDTO;
import com.example.orderservice.interfaces.dto.rating.RatingDto;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.ItemService;
import com.example.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;
    private final RatingClient ratingClient;
//    private final GRPCClientService grpcClientService;
    private final OrderCreatedSender orderCreatedSender;
    private final ProductClient productClient;
    private final OrderPaymentSender orderPaymentSender;

    public OrderController(OrderService orderService, ItemService itemService, RatingClient ratingClient, OrderCreatedSender orderCreatedSender, ProductClient productClient, OrderPaymentSender orderPaymentSender) {
        this.orderService = orderService;
        this.itemService = itemService;
        this.ratingClient = ratingClient;
//        this.grpcClientService = grpcClientService;
        this.orderCreatedSender = orderCreatedSender;
        this.productClient = productClient;
        this.orderPaymentSender = orderPaymentSender;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/order/create", consumes = "application/json")
    public Order saveNewOrder(@Valid @RequestBody Order order) {
        Order createdOrder = orderService.saveNewOrder(order);
        order.getItems().forEach(item -> item.setOrder(order));

        itemService.saveItems(order.getItems());

//        grpcClientService.sendSystemEvent("Order created", "INSERT", "Test User");
        return createdOrder;
    }

    @GetMapping(value = "/order/{id}")
    public Order getOrderById(@PathVariable Long id) {
        Order order = orderService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
//        grpcClientService.sendSystemEvent("Get order by id", "READ", "Test User");
        return order;
    }

    @GetMapping(value = "/order")
    public List<Order> getAllOrders() {
        List<Order> allOrders = orderService.findAll();
//        String eventId = grpcClientService.sendSystemEvent("Get all orders", "READ", "Test User");
//        System.out.println("Event ID returned by sytem-events: " + eventId);
        return allOrders;
    }

    @DeleteMapping(value = "/order/{id}")
    public void deleteOrderById(@PathVariable Long id) {
        orderService.deleteById(id);
//        grpcClientService.sendSystemEvent("Order delete by id", "DELETE", "Test User");
    }

    @GetMapping(value = "/order/by-user/{userId}")
    public List<Order> getAllOrdersByUser(@PathVariable Long userId) {
        List<Order> allOrdersByUser = orderService.findAllOrdersByUser(userId);
//        grpcClientService.sendSystemEvent("Get all orders by user", "READ", "Test User");
        return allOrdersByUser;
    }

    @PostMapping(value = "/order/rate")
    public void rateOrder(@Valid @RequestBody OrderRatingDto orderRatingDto) {
        for (OrderRatingItem ratingItem : orderRatingDto.getRatingItems()) {
            ratingClient.addRating(new RatingDto(ratingItem.getProductId(), orderRatingDto.getUserId(), ratingItem.getRating()));
        }
//        grpcClientService.sendSystemEvent("Order rate", "INSERT", "Test User");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/order/place", consumes = "application/json")
    public Order placeNewOrder(@Valid @RequestBody Order order) {
        Order createdOrder = orderService.saveNewOrder(order);

        order.getItems().forEach(item -> item.setOrder(order));

        itemService.saveItems(order.getItems());


//        grpcClientService.sendSystemEvent("Order created", "INSERT", "Test User");
        orderCreatedSender.sendOrderCreatedEvent(order);
        // POSALJI DOGADJAJ DA JE ORDER USPJESNO KREIRAN (KOJI CE PRODUCT SERVICE DA SLUSA KAKO BI SKINUO PROIZVOD SA LAGERA)

        return createdOrder;
    }

    @PostMapping(value = "/order/{id}/pay")
    public Order payOrder(@PathVariable Long id, @RequestParam String stripeToken) {
        // pozivamo product service da nam da cijenu cijelog ordera

        Order order = orderService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        List<OrderCreatedEvent.OrderItemInfo> orderItemInfoList = order
                .getItems()
                .stream()
                .map(item -> new OrderCreatedEvent.OrderItemInfo(item.getProduct().getId(), item.getAmount()))
                .collect(Collectors.toList());

        CalculatePriceDTO calculatePriceDTO = new CalculatePriceDTO(orderItemInfoList);
        Double amount = productClient.calculateOrderPrice(calculatePriceDTO);

        OrderPaymentEvent orderPaymentEvent = new OrderPaymentEvent(id, amount, stripeToken);

        orderPaymentSender.sendOrderCreatedEvent(orderPaymentEvent);

        return null;
        // posaljemo ovaj event preko rabbitmq
    }
}
