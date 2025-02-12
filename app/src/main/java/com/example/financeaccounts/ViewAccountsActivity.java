package com.example.financeaccounts;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class ViewAccountsActivity extends AppCompatActivity {
    private ListView listView;
    private FinanceDataSource financeDataSource;
    private ArrayList<String> accountList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_accounts);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listView = findViewById(R.id.listView);
        accountList = new ArrayList<>();
        financeDataSource = new FinanceDataSource(this);
        financeDataSource.open();
        loadAccounts();
    }
    private void loadAccounts() {
        List<CDaccount> cdAccounts = financeDataSource.getAllCDAccounts();
        List<LoanAccount> loanAccounts = financeDataSource.getAllLoanAccounts();
        List<CheckingAccount> checkingAccounts = financeDataSource.getAllCheckingAccounts();

        if (cdAccounts.isEmpty() && loanAccounts.isEmpty() && checkingAccounts.isEmpty()) {
            Toast.makeText(this, "No accounts found", Toast.LENGTH_SHORT).show();
            return;
        }

        for (CDaccount cd : cdAccounts) {
            accountList.add("CD Account #" + cd.getAccountNumber() + " - Balance: $" + cd.getCurrentBalance());
        }
        for (LoanAccount loan : loanAccounts) {
            accountList.add("Loan Account #" + loan.getAccountNumber() + " - Balance: $" + loan.getCurrentBalance());
        }
        for (CheckingAccount checking : checkingAccounts) {
            accountList.add("Checking Account #" + checking.getAccountNumber() + " - Balance: $" + checking.getCurrentBalance());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, accountList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        financeDataSource.close();
    }
}


