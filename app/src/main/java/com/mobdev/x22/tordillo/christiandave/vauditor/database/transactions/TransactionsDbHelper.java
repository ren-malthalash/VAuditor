package com.mobdev.x22.tordillo.christiandave.vauditor.database.transactions;

import static com.mobdev.x22.tordillo.christiandave.vauditor.database.DatabaseManager.DATABASE_NAME_VAUDITOR_DATA;
import static com.mobdev.x22.tordillo.christiandave.vauditor.database.DatabaseManager.GLOBAL_DATABASE_VERSION;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mobdev.x22.tordillo.christiandave.vauditor.database.balanceaccounts.BalanceAccountContract.BalanceAccountEntry;
import com.mobdev.x22.tordillo.christiandave.vauditor.database.transactions.TransactionContract.TransactionEntry;
import com.mobdev.x22.tordillo.christiandave.vauditor.database.transactions.TransactionGroupContract.TransactionGroupEntry;

import java.time.ZonedDateTime;

public class TransactionsDbHelper extends SQLiteOpenHelper {

    public static final String SQL_CREATE_TRANSACTION_ENTRIES =
            "CREATE TABLE " +
                    TransactionEntry.TABLE_NAME + " (" +
                    TransactionEntry._ID + " INTEGER PRIMARY KEY, " +
                    TransactionEntry.COLUMN_GROUP_ID + " INTEGER," +
                    TransactionEntry.COLUMN_NAME + " TEXT," +
                    TransactionEntry.COLUMN_AMOUNT +  " TEXT," +
                    TransactionEntry.COLUMN_NOTES + " TEXT," +
                        "FOREIGN KEY (" + TransactionEntry.COLUMN_GROUP_ID + ") " +
                        "REFERENCES " + TransactionGroupEntry.TABLE_NAME + "(_id) " +
                        "ON DELETE NO ACTION)";

    public static final String SQL_CREATE_TRANSACTION_GROUP_ENTRIES =
            "CREATE TABLE " +
                    TransactionGroupEntry.TABLE_NAME + " (" +
                    TransactionGroupEntry._ID + " INTEGER PRIMARY KEY, " +
                    TransactionGroupEntry.COLUMN_NAME + " TEXT," +
                    TransactionGroupEntry.COLUMN_SENDER + " INTEGER," +
                    TransactionGroupEntry.COLUMN_RECIPIENT +  " INTEGER," +
                    TransactionGroupEntry.COLUMN_NOTES + " TEXT," +
                    TransactionGroupEntry.COLUMN_DATE + " TEXT," +
                        "FOREIGN KEY (" + TransactionGroupEntry.COLUMN_SENDER + ") " +
                        "REFERENCES " + BalanceAccountEntry.TABLE_NAME + "(_id) " +
                        "ON DELETE NO ACTION," +
                        "FOREIGN KEY (" + TransactionGroupEntry.COLUMN_RECIPIENT + ") " +
                        "REFERENCES " + BalanceAccountEntry.TABLE_NAME + "(_id) " +
                        "ON DELETE NO ACTION)";

    private static final String SQL_DELETE_TRANSACTION_ENTRIES =
            "DROP TABLE IF EXISTS " + TransactionEntry.TABLE_NAME;

    private static final String SQL_DELETE_TRANSACTION_GROUP_ENTRIES =
            "DROP TABLE IF EXISTS " + TransactionGroupEntry.TABLE_NAME;


    public TransactionsDbHelper(Context context) {
        super(context, DATABASE_NAME_VAUDITOR_DATA, null, GLOBAL_DATABASE_VERSION);
    }

    public TransactionsDbHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("DB_CREATION", "Creating transaction table");
        sqLiteDatabase.execSQL(SQL_CREATE_TRANSACTION_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_TRANSACTION_GROUP_ENTRIES);
        insertInitialTransactionGroups(sqLiteDatabase);
        insertInitialTransactions(sqLiteDatabase);
    }

    public static void insertInitialTransactionGroups(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put(TransactionGroupEntry.COLUMN_NAME, "Group A");
        values.put(TransactionGroupEntry.COLUMN_SENDER, 1);
        values.put(TransactionGroupEntry.COLUMN_RECIPIENT, 2);
        values.put(TransactionGroupEntry.COLUMN_NOTES, "Group A - initial transactions");
        values.put(TransactionGroupEntry.COLUMN_DATE, ZonedDateTime.now().minusDays(10).toString());
        db.insert(TransactionGroupEntry.TABLE_NAME, null, values);

        values.clear();
        values.put(TransactionGroupEntry.COLUMN_NAME, "Group B");
        values.put(TransactionGroupEntry.COLUMN_SENDER, 2);
        values.put(TransactionGroupEntry.COLUMN_RECIPIENT, 3);
        values.put(TransactionGroupEntry.COLUMN_NOTES, "Group B - initial transactions");
        values.put(TransactionGroupEntry.COLUMN_DATE, ZonedDateTime.now().minusDays(8).toString());
        db.insert(TransactionGroupEntry.TABLE_NAME, null, values);

        values.clear();
        values.put(TransactionGroupEntry.COLUMN_NAME, "Group C");
        values.put(TransactionGroupEntry.COLUMN_SENDER, 3);
        values.put(TransactionGroupEntry.COLUMN_RECIPIENT, 4);
        values.put(TransactionGroupEntry.COLUMN_NOTES, "Group C - initial transactions");
        values.put(TransactionGroupEntry.COLUMN_DATE, ZonedDateTime.now().minusDays(5).toString());
        db.insert(TransactionGroupEntry.TABLE_NAME, null, values);
    }

    public static void insertInitialTransactions(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        // Transactions for Group A
        values.put(TransactionEntry.COLUMN_GROUP_ID, 1);
        values.put(TransactionEntry.COLUMN_NAME, "Payment to Supplier");
        values.put(TransactionEntry.COLUMN_AMOUNT, "150.00");
        values.put(TransactionEntry.COLUMN_NOTES, "Initial payment for supplies.");
        db.insert(TransactionEntry.TABLE_NAME, null, values);

        values.clear();
        values.put(TransactionEntry.COLUMN_GROUP_ID, 1);
        values.put(TransactionEntry.COLUMN_NAME, "Refund from Supplier");
        values.put(TransactionEntry.COLUMN_AMOUNT, "-50.00");
        values.put(TransactionEntry.COLUMN_NOTES, "Partial refund.");
        db.insert(TransactionEntry.TABLE_NAME, null, values);

        // Transactions for Group B
        values.clear();
        values.put(TransactionEntry.COLUMN_GROUP_ID, 2);
        values.put(TransactionEntry.COLUMN_NAME, "Purchase of Equipment");
        values.put(TransactionEntry.COLUMN_AMOUNT, "300.00");
        values.put(TransactionEntry.COLUMN_NOTES, "Initial equipment purchase.");
        db.insert(TransactionEntry.TABLE_NAME, null, values);

        values.clear();
        values.put(TransactionEntry.COLUMN_GROUP_ID, 2);
        values.put(TransactionEntry.COLUMN_NAME, "Service Payment");
        values.put(TransactionEntry.COLUMN_AMOUNT, "75.00");
        values.put(TransactionEntry.COLUMN_NOTES, "Monthly service fee.");
        db.insert(TransactionEntry.TABLE_NAME, null, values);

        // Transactions for Group C
        values.clear();
        values.put(TransactionEntry.COLUMN_GROUP_ID, 3);
        values.put(TransactionEntry.COLUMN_NAME, "Invoice Payment");
        values.put(TransactionEntry.COLUMN_AMOUNT, "250.00");
        values.put(TransactionEntry.COLUMN_NOTES, "Payment for invoice #123.");
        db.insert(TransactionEntry.TABLE_NAME, null, values);

        values.clear();
        values.put(TransactionEntry.COLUMN_GROUP_ID, 3);
        values.put(TransactionEntry.COLUMN_NAME, "Transfer to Savings");
        values.put(TransactionEntry.COLUMN_AMOUNT, "100.00");
        values.put(TransactionEntry.COLUMN_NOTES, "Savings transfer.");
        db.insert(TransactionEntry.TABLE_NAME, null, values);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_TRANSACTION_ENTRIES);
        sqLiteDatabase.execSQL(SQL_DELETE_TRANSACTION_GROUP_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
