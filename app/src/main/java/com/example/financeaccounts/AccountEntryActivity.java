package com.example.financeaccounts;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AccountEntryActivity extends AppCompatActivity {
    private EditText etAccountNumber;
    private EditText etBalance;
    private EditText etInterestRate;
    private EditText etPayment;
    private Button btnSave;
    private Button btnCancel;
    private RadioGroup radioGroupAccountType;
    private RadioButton radioChecking;
    private RadioButton radioCD;
    private RadioButton radioLoan;
    private FinanceDataSource financeDataSource;
    private FinanceDBHelper dbHelper;
    private SQLiteDatabase db;
    private static final String PREFS_NAME = "FinancePrefs";
    private static final String KEY_ACCOUNT_TYPE = "lastSelectedAccountType";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_entry);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
            // Initialize UI elements
            radioGroupAccountType = findViewById(R.id.radioGroupAccountType);
            etAccountNumber = findViewById(R.id.etAccountNumber);
            etBalance = findViewById(R.id.etBalance);
            etInterestRate = findViewById(R.id.etInterestRate);
            etPayment = findViewById(R.id.etPayment);
            btnSave = findViewById(R.id.btnSave);
            btnCancel = findViewById(R.id.btnCancel);

            // Initialize database
            financeDataSource = new FinanceDataSource(this);
            financeDataSource.open();

            // Restore last selected account type
            loadAccountTypePreference();

            // Handle account type selection
            radioGroupAccountType.setOnCheckedChangeListener((group, checkedId) -> {
                saveAccountTypePreference(checkedId); // Save user selection
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
            // Save button logic
            btnSave.setOnClickListener(v -> saveAccount());

            // Cancel button logic
            btnCancel.setOnClickListener(v -> clearFields());

        }

        private void saveAccount() {
            int accountNumber = Integer.parseInt(etAccountNumber.getText().toString());
            double balance = Double.parseDouble(etBalance.getText().toString());
            boolean success = false;

            int selectedId = radioGroupAccountType.getCheckedRadioButtonId();
            if (selectedId == R.id.radioCD) {
                double interestRate = Double.parseDouble(etInterestRate.getText().toString());
                CDaccount cdAccount = new CDaccount(accountNumber, balance, interestRate);
                success = financeDataSource.insertCDAccount(cdAccount);
            } else if (selectedId == R.id.radioLoan) {
                double interestRate = Double.parseDouble(etInterestRate.getText().toString());
                double payment = Double.parseDouble(etPayment.getText().toString());
                LoanAccount loanAccount = new LoanAccount(accountNumber, balance, interestRate, payment);
                success = financeDataSource.insertLoanAccount(loanAccount);
            } else if (selectedId == R.id.radioChecking) {
                CheckingAccount checkingAccount = new CheckingAccount(accountNumber, balance);
                success = financeDataSource.insertCheckingAccount(checkingAccount);
            }

            if (success) {
                Toast.makeText(this, "Account saved successfully!", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(this, "Error saving account!", Toast.LENGTH_SHORT).show();
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

        // Save selected account type to SharedPreferences
        private void saveAccountTypePreference(int selectedId) {
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(KEY_ACCOUNT_TYPE, selectedId);
            editor.apply();
        }

        // Load and set the last selected account type
        private void loadAccountTypePreference() {
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            int savedAccountType = preferences.getInt(KEY_ACCOUNT_TYPE, -1);
            if (savedAccountType != -1) {
                radioGroupAccountType.check(savedAccountType);
            }
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            financeDataSource.close();
        }
}
}