package com.example.financeaccounts;

public class CheckingAccount {
    private int accountNumber;
    private double currentBalance;

    // Constructor
    public CheckingAccount(int accountNumber, double currentBalance) {
        this.accountNumber = accountNumber;
        this.currentBalance = currentBalance;
    }

    // Getters and Setters
    public int getAccountNumber() { return accountNumber; }
    public void setAccountNumber(int accountNumber) { this.accountNumber = accountNumber; }

    public double getCurrentBalance() { return currentBalance; }
    public void setCurrentBalance(double currentBalance) { this.currentBalance = currentBalance; }

    // Display Account Details
    public String toString() {
        return "Checking Account #" + accountNumber + " | Balance: $" + currentBalance;
    }
}
