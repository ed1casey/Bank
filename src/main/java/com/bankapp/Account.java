package com.bankapp;

public class Account {
    private String accountNumber;
    private double balance;

    public Account() {
        this.accountNumber = generateUniqueNumber();
        this.balance = 0.0;
    }

    private String generateUniqueNumber() {
        return "ACCT" + System.nanoTime();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }
}

