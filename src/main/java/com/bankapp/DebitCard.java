package com.bankapp;

public class DebitCard {
    private String cardNumber;
    private String expiryDate;
    private double balance;

    public DebitCard() {
        this.cardNumber = generateUniqueNumber();
        this.expiryDate = "12/25";
        this.balance = 0.0;
    }

    private String generateUniqueNumber() {
        return "CARD" + System.nanoTime();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }
}

