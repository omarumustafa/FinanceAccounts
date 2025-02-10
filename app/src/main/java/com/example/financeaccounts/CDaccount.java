package com.example.financeaccounts;

public class CDaccount {
    private int accountNumber;
    private double initialBalance;
    private double interestRate;
    private double currentBalance;

    public CDaccount(int accountNumber, double initialBalance, double interestRate) {
        this.accountNumber = accountNumber;
        this.initialBalance = initialBalance;
        this.interestRate = interestRate;
        this.currentBalance = initialBalance;
    }
    public int getAccountNumber() { return accountNumber; }
    public void setAccountNumber(int accountNumber) { this.accountNumber = accountNumber; }

    public double getInitialBalance() { return initialBalance; }
    public void setInitialBalance(double initialBalance) { this.initialBalance = initialBalance; }

    public double getCurrentBalance() { return currentBalance; }
    public void setCurrentBalance(double currentBalance) { this.currentBalance = currentBalance; }

    public double getInterestRate() { return interestRate; }
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }

    @Override
    public String toString() {
        return "CDAccount{" +
                "accountNumber=" + accountNumber +
                ", initialBalance=" + initialBalance +
                ", currentBalance=" + currentBalance +
                ", interestRate=" + interestRate +
                '}';
    }
}

