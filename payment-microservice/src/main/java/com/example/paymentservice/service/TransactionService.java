package com.example.paymentservice.service;

import com.example.paymentservice.model.Transaction;
import com.example.paymentservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private static final String OKAY_STRIPE_TOKEN = "111111111";


    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    public Transaction processPayment(Long orderId, Double amount, String stripeToken) {
        // komunikacija sa stripe-om gdje ovaj mikroservis pokusava da naplati korisniku odredjeni iznos

        // mi ovo mockujemo u ovom trenutku


        Transaction.TransactionStatus status = stripeToken.equals(OKAY_STRIPE_TOKEN)
                ? Transaction.TransactionStatus.Paid
                : Transaction.TransactionStatus.Failed;

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setOrderId(orderId);
        transaction.setTime(LocalDateTime.now());
        transaction.setTransactionCode(UUID.randomUUID().toString());
        transaction.setStatus(status);

        return transactionRepository.save(transaction);
    }
}
