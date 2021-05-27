package com.example.orderservice.amqp;


import com.example.orderservice.amqp.event.OrderPaymentEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderPaymentSender {

    private final RabbitTemplate rabbitTemplate;

    public OrderPaymentSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrderCreatedEvent(OrderPaymentEvent paymentEvent) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String message = objectMapper.writeValueAsString(paymentEvent);
            rabbitTemplate.convertAndSend("order_payment_queue", message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();;
        }

    }
}
