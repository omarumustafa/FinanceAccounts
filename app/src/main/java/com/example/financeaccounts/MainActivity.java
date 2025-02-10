package com.example.financeaccounts;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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

        if (db != null) {
            Log.d("DB_TEST", "Database created successfully.");
        } else {
            Log.d("DB_TEST", "Database creation failed.");
        }
    }



    }


