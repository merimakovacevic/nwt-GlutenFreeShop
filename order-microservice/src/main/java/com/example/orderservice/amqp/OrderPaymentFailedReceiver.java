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

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RabbitListener(queues = "order_payment_failed_queue")
public class OrderPaymentFailedReceiver {

    private final OrderService orderService;
    private final RabbitTemplate rabbitTemplate;

    public OrderPaymentFailedReceiver(OrderService orderService, RabbitTemplate rabbitTemplate) {
        this.orderService = orderService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitHandler
    @Transactional
    public void receive(String message) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            OrderPaymentFailedEvent orderPaymentFailedEvent = objectMapper.readValue(message, OrderPaymentFailedEvent.class);
            orderService.markOrderPaymentFailed(orderPaymentFailedEvent.getOrderId());

            Order order = orderService.findById(orderPaymentFailedEvent.getOrderId()).orElseThrow(()
                    -> new RuntimeException("Mark order payment failed - orderId does not exist: " + orderPaymentFailedEvent.getOrderId()));


            List<OrderCreatedEvent.OrderItemInfo> orderItemInfoList = order
                    .getItems()
                    .stream()
                    .map(item -> new OrderCreatedEvent.OrderItemInfo(item.getProduct().getId(), item.getAmount()))
                    .collect(Collectors.toList());

            rabbitTemplate.convertAndSend("order_stock_return_queue", objectMapper.writeValueAsString(new OrderStockReturnEvent(orderItemInfoList)));

        } catch (JsonProcessingException ignored2) {
        }
    }
}

