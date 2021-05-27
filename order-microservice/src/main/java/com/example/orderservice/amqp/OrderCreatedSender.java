package com.example.orderservice.amqp;


import com.example.orderservice.amqp.event.OrderCreatedEvent;
import com.example.orderservice.amqp.event.OrderCreatedEvent.OrderItemInfo;
import com.example.orderservice.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderCreatedSender {

    private final RabbitTemplate rabbitTemplate;

    public OrderCreatedSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrderCreatedEvent(Order order) {
        ObjectMapper objectMapper = new ObjectMapper();

//        List<OrderItemInfo> orderItemInfoList = new ArrayList<>();
//
//        for (Item item : order.getItems()) {
//            orderItemInfoList.add(new OrderItemInfo(item.getProduct().getId(), item.getAmount()));
//        }

        List<OrderItemInfo> orderItemInfoList = order
                .getItems()
                .stream()
                .map(item -> new OrderItemInfo(item.getProduct().getId(), item.getAmount()))
                .collect(Collectors.toList());


        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(order.getId(), orderItemInfoList);


        try {
            String message = objectMapper.writeValueAsString(orderCreatedEvent);
            rabbitTemplate.convertAndSend("order_created_queue", message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();;
        }

    }
}
