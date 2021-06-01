package com.example.orderservice.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderProcessConfiguration {

    @Bean(name = "order_processed_queue")
    public Queue orderProcessedQueue() {
        return new Queue("order_processed_queue");
    }

    @Bean(name = "order_out_of_stock_queue")
    public Queue orderOutOfStockQueue() {
        return new Queue("order_out_of_stock_queue");
    }

    @Bean(name = "order_paid_queue")
    public Queue orderPaidQueue() {
        return new Queue("order_paid_queue");
    }

    @Bean(name = "order_payment_failed_queue")
    public Queue orderPaymentFailedQuque() {
        return new Queue("order_payment_failed_queue");
    }
}
