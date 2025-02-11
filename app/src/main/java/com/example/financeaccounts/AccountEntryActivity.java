package com.example.financeaccounts;





import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AccountEntryActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "FinancePrefs";
    private static final String KEY_ACCOUNT_TYPE = "lastSelectedAccountType";

    private RadioGroup radioGroupAccountType;
    private EditText etAccountNumber, etBalance, etInterestRate, etPayment;
    private Button btnSave, btnCancel, btnHome;
    private FinanceDataSource financeDataSource;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_entry);

        // Initialize UI elements
        radioGroupAccountType = findViewById(R.id.radioGroupAccountType);
        etAccountNumber = findViewById(R.id.etAccountNumber);
        etBalance = findViewById(R.id.etBalance);
        etInterestRate = findViewById(R.id.etInterestRate);
        etPayment = findViewById(R.id.etPayment);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnHome = findViewById(R.id.btnHome);

        // Initialize database
        financeDataSource = new FinanceDataSource(this);
        financeDataSource.open();

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        loadAccountTypePreference();

        // Handle account type selection
        radioGroupAccountType.setOnCheckedChangeListener((group, checkedId) -> {
            saveAccountTypePreference(checkedId); // Save user selection
            updateFieldVisibility(checkedId);
        });

        // Save button logic
        btnSave.setOnClickListener(v -> {
            if (radioGroupAccountType.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please select an account type!", Toast.LENGTH_SHORT).show();
            } else {
                saveAccount();
            }
        });

        // Cancel button logic
        btnCancel.setOnClickListener(v -> clearFields());

        // Home button logic
        btnHome.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
    }

    private void updateFieldVisibility(int checkedId) {
        runOnUiThread(() -> {
            if (checkedId == R.id.radioCD) {
                etInterestRate.setVisibility(View.VISIBLE);
                etPayment.setVisibility(View.GONE);
            } else if (checkedId == R.id.radioLoan) {
                etInterestRate.setVisibility(View.VISIBLE);
                etPayment.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.radioChecking) {
                etInterestRate.setVisibility(View.GONE);
                etPayment.setVisibility(View.GONE);
            }
        });
    }

    private void saveAccount() {
        try {
            int accountNumber = Integer.parseInt(etAccountNumber.getText().toString());
            double balance = Double.parseDouble(etBalance.getText().toString());
            boolean success = false;

            int selectedId = radioGroupAccountType.getCheckedRadioButtonId();
            if (selectedId == R.id.radioCD) {
                double interestRate = Double.parseDouble(etInterestRate.getText().toString());
                success = financeDataSource.insertCDAccount(new CDaccount(accountNumber, balance, interestRate));
            } else if (selectedId == R.id.radioLoan) {
                double interestRate = Double.parseDouble(etInterestRate.getText().toString());
                double payment = Double.parseDouble(etPayment.getText().toString());
                success = financeDataSource.insertLoanAccount(new LoanAccount(accountNumber, balance, interestRate, payment));
            } else {
                success = financeDataSource.insertCheckingAccount(new CheckingAccount(accountNumber, balance));
            }

            Toast.makeText(this, success ? "Account saved successfully!" : "Error saving account!", Toast.LENGTH_SHORT).show();
            if (success) clearFields();

        } catch (Exception e) {
            Toast.makeText(this, "Invalid input! Please check your entries.", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        etAccountNumber.setText("");
        etBalance.setText("");
        etInterestRate.setText("");
        etPayment.setText("");
        etInterestRate.setVisibility(View.GONE);
        etPayment.setVisibility(View.GONE);
        radioGroupAccountType.clearCheck();
    }

    private void saveAccountTypePreference(int selectedId) {
        sharedPreferences.edit().putInt(KEY_ACCOUNT_TYPE, selectedId).apply();
    }

    private void loadAccountTypePreference() {
        int savedAccountType = sharedPreferences.getInt(KEY_ACCOUNT_TYPE, -1);
        if (savedAccountType != -1) {
            radioGroupAccountType.check(savedAccountType);
            updateFieldVisibility(savedAccountType);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        financeDataSource.close();
    }
}
