package com.example.orderservice.amqp.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaymentEvent {

    private Long orderId;
    private Double amount;
    private String stripeToken;
}
