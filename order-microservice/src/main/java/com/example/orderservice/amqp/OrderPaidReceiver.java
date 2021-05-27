package com.example.orderservice.amqp;


import com.example.orderservice.amqp.event.OrderCreatedEvent;
import com.example.orderservice.amqp.event.OrderPaidEvent;
import com.example.orderservice.amqp.event.OrderPaymentFailedEvent;
import com.example.orderservice.amqp.event.OrderStockReturnEvent;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RabbitListener(queues = {"order_paid_queue"})
public class OrderPaidReceiver {

    private final OrderService orderService;

    public OrderPaidReceiver(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitHandler
    public void receive(String message) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            OrderPaidEvent orderProcessedEvent = objectMapper.readValue(message, OrderPaidEvent.class);
            orderService.markOrderPaid(orderProcessedEvent.getOrderId());

        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
    }
}
