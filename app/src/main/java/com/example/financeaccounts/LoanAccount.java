package com.example.financeaccounts;

public class LoanAccount {
    private int accountNumber;
    private double initialBalance;
    private double currentBalance;
    private double paymentAmount;
    private double interestRate;

    // Constructor
    public LoanAccount(int accountNumber, double initialBalance, double interestRate, double paymentAmount) {
        this.accountNumber = accountNumber;
        this.initialBalance = initialBalance;
        this.currentBalance = initialBalance;
        this.interestRate = interestRate;
        this.paymentAmount = paymentAmount;
    }

    // Getters and Setters
    public int getAccountNumber() { return accountNumber; }
    public void setAccountNumber(int accountNumber) { this.accountNumber = accountNumber; }

    public double getInitialBalance() { return initialBalance; }
    public void setInitialBalance(double initialBalance) { this.initialBalance = initialBalance; }

    public double getCurrentBalance() { return currentBalance; }
    public void setCurrentBalance(double currentBalance) { this.currentBalance = currentBalance; }

    public double getInterestRate() { return interestRate; }
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }

    public double getPaymentAmount() { return paymentAmount; }
    public void setPaymentAmount(double paymentAmount) { this.paymentAmount = paymentAmount; }

    // Display Account Details
    public String toString() {
        return "Loan Account #" + accountNumber + " | Balance: $" + currentBalance + " | Payment: $" + paymentAmount + " | Rate: " + interestRate + "%";
    }
}