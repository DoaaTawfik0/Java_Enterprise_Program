package com.doaatawfik.services;


import com.doaatawfik.exceptions.InsufficientCreditException;
import lombok.Getter;

@Getter
public class CreditCardPayment implements Payment {
    private double availableLimit;


    public CreditCardPayment(double availableLimit) {
        validateBalance();

        this.availableLimit = availableLimit;
    }

    public void validateBalance() {
        if (availableLimit < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
    }


    @Override
    public void pay(double amount) {
        validateAmount(amount);

        if (amount > availableLimit) {
            throw new InsufficientCreditException("Insufficient credit amount. Available: " + availableLimit);
        }

        availableLimit -= amount;


        System.out.println("Credit Card Payment successful - " + amount);
    }

    private void validateAmount(double amount) {
        if (!Double.isFinite(amount) || amount < 0) {
            throw new IllegalArgumentException("Invalid payment amount");
        }
    }

}
