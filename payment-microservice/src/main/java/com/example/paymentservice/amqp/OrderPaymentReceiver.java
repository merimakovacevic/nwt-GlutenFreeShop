package com.example.paymentservice.amqp;


import com.example.paymentservice.amqp.event.OrderPaidEvent;
import com.example.paymentservice.amqp.event.OrderPaymentEvent;
import com.example.paymentservice.amqp.event.OrderPaymentFailedEvent;
import com.example.paymentservice.model.Transaction;
import com.example.paymentservice.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "order_payment_queue")
public class OrderPaymentReceiver {

    private final TransactionService transactionService;
    private final RabbitTemplate rabbitTemplate;

    public OrderPaymentReceiver(TransactionService transactionService, RabbitTemplate rabbitTemplate) {
        this.transactionService = transactionService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitHandler
    public void receive(String message) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            OrderPaymentEvent orderPaymentEvent = objectMapper.readValue(message, OrderPaymentEvent.class);
            Transaction transaction = transactionService.processPayment(orderPaymentEvent.getOrderId(), orderPaymentEvent.getAmount(), orderPaymentEvent.getStripeToken());

            if (transaction.getStatus().equals(Transaction.TransactionStatus.Paid)) {



                rabbitTemplate.convertAndSend("order_paid_queue", objectMapper.writeValueAsString(new OrderPaidEvent(transaction.getOrderId())));
            }
            else {

                rabbitTemplate.convertAndSend("order_payment_failed_queue", objectMapper.writeValueAsString(new OrderPaymentFailedEvent(transaction.getOrderId())));
            }
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();;
        }
    }
}
