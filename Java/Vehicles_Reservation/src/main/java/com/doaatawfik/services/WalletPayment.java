package com.doaatawfik.services;

import com.doaatawfik.exceptions.InsufficientBalanceException;
import lombok.Getter;

@Getter
public class WalletPayment implements Payment {

    private double balance;


    public WalletPayment(double balance) {
        validateBalance();

        this.balance = balance;
    }

    public void validateBalance() {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
    }

    private void validateAmount(double amount) {
        if (!Double.isFinite(amount) || amount < 0) {
            throw new IllegalArgumentException("Invalid payment amount");
        }
    }


    @Override
    public void pay(double amount) {

        validateAmount(amount);

        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient wallet balance. Available: " + balance);
        }
        balance -= amount;

        System.out.println("Wallet Payment successful - " + amount);
    }
}
