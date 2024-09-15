package com.bankapp;

public class User {
    private String username;
    private String password;
    private Account savingsAccount;
    private DebitCard debitCard;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.savingsAccount = new Account();
        this.debitCard = new DebitCard();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Account getSavingsAccount() {
        return savingsAccount;
    }

    public DebitCard getDebitCard() {
        return debitCard;
    }
}


