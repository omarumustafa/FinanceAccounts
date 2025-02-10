package com.example.financeaccounts;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FinanceDBHelper extends SQLiteOpenHelper {
    // Database name and version
    private static final String DATABASE_NAME = "myfinances.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_CD = "cd_accounts";
    public static final String TABLE_LOAN = "loan_accounts";
    public static final String TABLE_CHECKING = "checking_accounts";

    // Common Column
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ACCOUNT_NUMBER = "account_number";
    public static final String COLUMN_CURRENT_BALANCE = "current_balance";

    // CD Account Columns
    public static final String COLUMN_INITIAL_BALANCE = "initial_balance";
    public static final String COLUMN_INTEREST_RATE = "interest_rate";

    // Loan Account Columns
    public static final String COLUMN_PAYMENT_AMOUNT = "payment_amount";

    // Table creation SQL statements
    private static final String CREATE_TABLE_CD = "CREATE TABLE " + TABLE_CD + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ACCOUNT_NUMBER + " INTEGER NOT NULL, " +
            COLUMN_INITIAL_BALANCE + " REAL, " +
            COLUMN_CURRENT_BALANCE + " REAL, " +
            COLUMN_INTEREST_RATE + " REAL);";

    private static final String CREATE_TABLE_LOAN = "CREATE TABLE " + TABLE_LOAN + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ACCOUNT_NUMBER + " INTEGER NOT NULL, " +
            COLUMN_INITIAL_BALANCE + " REAL, " +
            COLUMN_CURRENT_BALANCE + " REAL, " +
            COLUMN_INTEREST_RATE + " REAL, " +
            COLUMN_PAYMENT_AMOUNT + " REAL);";

    private static final String CREATE_TABLE_CHECKING = "CREATE TABLE " + TABLE_CHECKING + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ACCOUNT_NUMBER + " INTEGER NOT NULL, " +
            COLUMN_CURRENT_BALANCE + " REAL);";

    // Constructor
    public FinanceDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CD);
        db.execSQL(CREATE_TABLE_LOAN);
        db.execSQL(CREATE_TABLE_CHECKING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(FinanceDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECKING);
        onCreate(db);
    }
}
