package com.mobdev.x22.tordillo.christiandave.vauditor.database.balanceaccounts;

import static com.mobdev.x22.tordillo.christiandave.vauditor.database.DatabaseManager.DATABASE_NAME_VAUDITOR_DATA;
import static com.mobdev.x22.tordillo.christiandave.vauditor.database.DatabaseManager.GLOBAL_DATABASE_VERSION;
import static com.mobdev.x22.tordillo.christiandave.vauditor.database.notifications.NotificationsDbHelper.SQL_CREATE_NOTIFICATION_ENTRIES;
import static com.mobdev.x22.tordillo.christiandave.vauditor.database.notifications.NotificationsDbHelper.insertInitialData;
import static com.mobdev.x22.tordillo.christiandave.vauditor.database.transactions.TransactionsDbHelper.SQL_CREATE_TRANSACTION_ENTRIES;
import static com.mobdev.x22.tordillo.christiandave.vauditor.database.transactions.TransactionsDbHelper.SQL_CREATE_TRANSACTION_GROUP_ENTRIES;
import static com.mobdev.x22.tordillo.christiandave.vauditor.database.transactions.TransactionsDbHelper.insertInitialTransactionGroups;
import static com.mobdev.x22.tordillo.christiandave.vauditor.database.transactions.TransactionsDbHelper.insertInitialTransactions;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mobdev.x22.tordillo.christiandave.vauditor.database.balanceaccounts.BalanceAccountContract.BalanceAccountEntry;
import com.mobdev.x22.tordillo.christiandave.vauditor.database.transactions.TransactionGroupContract;

import java.time.ZonedDateTime;

public class BalanceAccountDbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " +
                BalanceAccountEntry.TABLE_NAME + " (" +
                BalanceAccountEntry._ID + " INTEGER PRIMARY KEY," +
                BalanceAccountEntry.COLUMN_NAME + " TEXT NOT NULL," +
                BalanceAccountEntry.COLUMN_TYPE + " INTEGER," +
                BalanceAccountEntry.COLUMN_BALANCE + " TEXT," +
                BalanceAccountEntry.COLUMN_DELETED + " TINYINT NOT NULL default 0)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BalanceAccountEntry.TABLE_NAME;

    public BalanceAccountDbHelper(Context context) {
        super(context, DATABASE_NAME_VAUDITOR_DATA, null, GLOBAL_DATABASE_VERSION);
    }

    public BalanceAccountDbHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("DB_CREATION", "Creating balanceAccount table");
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_TRANSACTION_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_TRANSACTION_GROUP_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_NOTIFICATION_ENTRIES);
        insertInitialAccounts(sqLiteDatabase);
        insertInitialTransactionGroups(sqLiteDatabase);
        insertInitialTransactions(sqLiteDatabase);
        insertInitialData(sqLiteDatabase);
    }

    private void insertInitialAccounts(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put(BalanceAccountEntry.COLUMN_NAME, "Cash");
        values.put(BalanceAccountEntry.COLUMN_TYPE, "Person");
        values.put(BalanceAccountEntry.COLUMN_BALANCE, "0");
        values.put(BalanceAccountEntry.COLUMN_DELETED, 0);
        db.insert(BalanceAccountEntry.TABLE_NAME, null, values);

        values.put(BalanceAccountEntry.COLUMN_NAME, "BDO");
        values.put(BalanceAccountEntry.COLUMN_TYPE, "Banking Company");
        values.put(BalanceAccountEntry.COLUMN_BALANCE, "0");
        values.put(BalanceAccountEntry.COLUMN_DELETED, 0);
        db.insert(BalanceAccountEntry.TABLE_NAME, null, values);

        values.put(BalanceAccountEntry.COLUMN_NAME, "GCash");
        values.put(BalanceAccountEntry.COLUMN_TYPE, "Banking Company");
        values.put(BalanceAccountEntry.COLUMN_BALANCE, "0");
        values.put(BalanceAccountEntry.COLUMN_DELETED, 0);
        db.insert(BalanceAccountEntry.TABLE_NAME, null, values);

        values.put(BalanceAccountEntry.COLUMN_NAME, "Security Bank");
        values.put(BalanceAccountEntry.COLUMN_TYPE, "Banking Company");
        values.put(BalanceAccountEntry.COLUMN_BALANCE, "0");
        values.put(BalanceAccountEntry.COLUMN_DELETED, 0);
        db.insert(BalanceAccountEntry.TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
