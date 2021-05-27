package com.example.paymentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    public enum Currency {
        EUR, USD;
    }

    public enum TransactionStatus {
        Failed, Paid
    }



//    private String secretApiKey; //Check if this is necessary
//    private String description;
    private Double amount;
//    private Currency currency;
//    private String stripeEmail;
//    private String stripeToken;
    private Long orderId;
    private LocalDateTime time;
    private String transactionCode;
    private TransactionStatus status;
}