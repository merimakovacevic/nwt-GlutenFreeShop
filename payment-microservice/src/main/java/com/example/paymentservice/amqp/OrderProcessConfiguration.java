package com.example.paymentservice.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderProcessConfiguration {

    @Bean(name = "order_payment_queue")
    public Queue orderPaymentQueue() {
        return new Queue("order_payment_queue");
    }
}
