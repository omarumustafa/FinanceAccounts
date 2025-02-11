package com.example.financeaccounts;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FinanceDataSource financeDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        CDaccount myCd = new CDaccount(1001, 5000.00, 2.5);
        Log.d("TEST", myCd.toString());

        CheckingAccount myChecking = new CheckingAccount(1002, 1000.00);
        Log.d("TEST", myChecking.toString());

        LoanAccount myLoan = new LoanAccount(1003, 10000.00, 5.0, 500.00);
        Log.d("TEST", myLoan.toString());

        FinanceDBHelper dbHelper = new FinanceDBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Initialize database connection
        financeDataSource = new FinanceDataSource(this);
        financeDataSource.open();


        // Insert sample accounts for testing
        insertSampleData();

        // Retrieve and log stored data
        retrieveAndLogData();

        // Close database
        financeDataSource.close();

    }
    private void insertSampleData() {
        // Insert CD Account
        CDaccount cd = new CDaccount(101, 5000.0, 2.5);
        boolean cdInserted = financeDataSource.insertCDAccount(cd);
        Log.d("DB_TEST", "Inserted CD Account: " + cdInserted);

        // Insert Loan Account
        LoanAccount loan = new LoanAccount(201, 10000.0, 5.0, 500.0);
        boolean loanInserted = financeDataSource.insertLoanAccount(loan);
        Log.d("DB_TEST", "Inserted Loan Account: " + loanInserted);

        // Insert Checking Account
        CheckingAccount checking = new CheckingAccount(301, 2500.0);
        boolean checkingInserted = financeDataSource.insertCheckingAccount(checking);
        Log.d("DB_TEST", "Inserted Checking Account: " + checkingInserted);
    }

    private void retrieveAndLogData() {
        // Retrieve and log CD accounts
        List<CDaccount> cdAccounts = financeDataSource.getAllCDAccounts();
        for (CDaccount cd : cdAccounts) {
            Log.d("DB_TEST", "CD: " + cd.toString());
        }

        // Retrieve and log Loan accounts
        List<LoanAccount> loanAccounts = financeDataSource.getAllLoanAccounts();
        for (LoanAccount loan : loanAccounts) {
            Log.d("DB_TEST", "Loan: " + loan.toString());
        }

        // Retrieve and log Checking accounts
        List<CheckingAccount> checkingAccounts = financeDataSource.getAllCheckingAccounts();
        for (CheckingAccount checking : checkingAccounts) {
            Log.d("DB_TEST", "Checking: " + checking.toString());
        }
    }



    }


