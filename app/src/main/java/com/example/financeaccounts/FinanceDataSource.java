package com.example.financeaccounts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FinanceDataSource {
    private SQLiteDatabase database;
    private FinanceDBHelper dbHelper;

    // Constructor
    public FinanceDataSource(Context context) {
        dbHelper = new FinanceDBHelper(context);
    }

    // Open database
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Close database
    public void close() {
        dbHelper.close();
    }

    // Insert CD Account
    public boolean insertCDAccount(CDaccount cd) {
        ContentValues values = new ContentValues();
        values.put(FinanceDBHelper.COLUMN_ACCOUNT_NUMBER, cd.getAccountNumber());
        values.put(FinanceDBHelper.COLUMN_INITIAL_BALANCE, cd.getInitialBalance());
        values.put(FinanceDBHelper.COLUMN_CURRENT_BALANCE, cd.getCurrentBalance());
        values.put(FinanceDBHelper.COLUMN_INTEREST_RATE, cd.getInterestRate());

        long result = database.insert(FinanceDBHelper.TABLE_CD, null, values);
        return result != -1; // Returns true if successful
    }

    // Insert Loan Account
    public boolean insertLoanAccount(LoanAccount loan) {
        ContentValues values = new ContentValues();
        values.put(FinanceDBHelper.COLUMN_ACCOUNT_NUMBER, loan.getAccountNumber());
        values.put(FinanceDBHelper.COLUMN_INITIAL_BALANCE, loan.getInitialBalance());
        values.put(FinanceDBHelper.COLUMN_CURRENT_BALANCE, loan.getCurrentBalance());
        values.put(FinanceDBHelper.COLUMN_INTEREST_RATE, loan.getInterestRate());
        values.put(FinanceDBHelper.COLUMN_PAYMENT_AMOUNT, loan.getPaymentAmount());

        long result = database.insert(FinanceDBHelper.TABLE_LOAN, null, values);
        return result != -1;
    }

    // Insert Checking Account
    public boolean insertCheckingAccount(CheckingAccount checking) {
        ContentValues values = new ContentValues();
        values.put(FinanceDBHelper.COLUMN_ACCOUNT_NUMBER, checking.getAccountNumber());
        values.put(FinanceDBHelper.COLUMN_CURRENT_BALANCE, checking.getCurrentBalance());

        long result = database.insert(FinanceDBHelper.TABLE_CHECKING, null, values);
        return result != -1;
    }

    // Retrieve all CD accounts
    public List<CDaccount> getAllCDAccounts() {
        List<CDaccount> cdAccounts = new ArrayList<>();
        Cursor cursor = database.query(FinanceDBHelper.TABLE_CD, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int accountNumber = cursor.getInt(cursor.getColumnIndexOrThrow(FinanceDBHelper.COLUMN_ACCOUNT_NUMBER));
                double initialBalance = cursor.getDouble(cursor.getColumnIndexOrThrow(FinanceDBHelper.COLUMN_INITIAL_BALANCE));
                double currentBalance = cursor.getDouble(cursor.getColumnIndexOrThrow(FinanceDBHelper.COLUMN_CURRENT_BALANCE));
                double interestRate = cursor.getDouble(cursor.getColumnIndexOrThrow(FinanceDBHelper.COLUMN_INTEREST_RATE));

                CDaccount cd = new CDaccount(accountNumber, initialBalance, interestRate);
                cd.setCurrentBalance(currentBalance);
                cdAccounts.add(cd);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cdAccounts;
    }

    // Retrieve all Loan accounts
    public List<LoanAccount> getAllLoanAccounts() {
        List<LoanAccount> loanAccounts = new ArrayList<>();
        Cursor cursor = database.query(FinanceDBHelper.TABLE_LOAN, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int accountNumber = cursor.getInt(cursor.getColumnIndexOrThrow(FinanceDBHelper.COLUMN_ACCOUNT_NUMBER));
                double initialBalance = cursor.getDouble(cursor.getColumnIndexOrThrow(FinanceDBHelper.COLUMN_INITIAL_BALANCE));
                double currentBalance = cursor.getDouble(cursor.getColumnIndexOrThrow(FinanceDBHelper.COLUMN_CURRENT_BALANCE));
                double interestRate = cursor.getDouble(cursor.getColumnIndexOrThrow(FinanceDBHelper.COLUMN_INTEREST_RATE));
                double paymentAmount = cursor.getDouble(cursor.getColumnIndexOrThrow(FinanceDBHelper.COLUMN_PAYMENT_AMOUNT));

                LoanAccount loan = new LoanAccount(accountNumber, initialBalance, interestRate, paymentAmount);
                loan.setCurrentBalance(currentBalance);
                loanAccounts.add(loan);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return loanAccounts;
    }

    // Retrieve all Checking accounts
    public List<CheckingAccount> getAllCheckingAccounts() {
        List<CheckingAccount> checkingAccounts = new ArrayList<>();
        Cursor cursor = database.query(FinanceDBHelper.TABLE_CHECKING, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int accountNumber = cursor.getInt(cursor.getColumnIndexOrThrow(FinanceDBHelper.COLUMN_ACCOUNT_NUMBER));
                double currentBalance = cursor.getDouble(cursor.getColumnIndexOrThrow(FinanceDBHelper.COLUMN_CURRENT_BALANCE));

                CheckingAccount checking = new CheckingAccount(accountNumber, currentBalance);
                checkingAccounts.add(checking);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return checkingAccounts;
    }
}

