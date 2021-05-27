package com.example.orderservice.amqp;


import com.example.orderservice.amqp.event.OrderOutOfStockEvent;
import com.example.orderservice.amqp.event.OrderProcessedEvent;
import com.example.orderservice.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = {"order_processed_queue", "order_out_of_stock_queue"})
public class OrderProcessingReceiver {

    private final OrderService orderService;

    public OrderProcessingReceiver(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitHandler
    public void receive(String message) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            OrderProcessedEvent orderProcessedEvent = objectMapper.readValue(message, OrderProcessedEvent.class);
            orderService.markOrderProcessed(orderProcessedEvent.getOrderId());

        } catch (JsonProcessingException ignored) {
            try {
                OrderOutOfStockEvent orderOutOfStockEvent = objectMapper.readValue(message, OrderOutOfStockEvent.class);
                orderService.markOrderOutOfStock(orderOutOfStockEvent.getOrderId(), orderOutOfStockEvent.getProductIds());
            } catch (JsonProcessingException ignored2) {
            }
        }
    }
}
