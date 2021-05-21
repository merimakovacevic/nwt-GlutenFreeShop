package com.example.paymentservice.amqp.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaymentEvent {

    private Long orderId;
    private Double amount;
    private String stripeToken;
}
