package com.example.paymentservice.model;

import lombok.Data;

@Data
public class Charge {

    public enum Currency {
        EUR, USD;
    }

    private String secretApiKey; //Check if this is necessary
    private String description;
    private int amount;
    private Currency currency;
    private String stripeEmail;
    private String stripeToken;
}