package com.mobdev.x22.tordillo.christiandave.vauditor.database.balanceaccounts;

import static com.mobdev.x22.tordillo.christiandave.vauditor.database.DatabaseManager.DATABASE_NAME_VAUDITOR_DATA;
import static com.mobdev.x22.tordillo.christiandave.vauditor.database.DatabaseManager.GLOBAL_DATABASE_VERSION;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobdev.x22.tordillo.christiandave.vauditor.database.balanceaccounts.BalanceAccountContract.BalanceAccountEntry;
import com.mobdev.x22.tordillo.christiandave.vauditor.database.transactions.TransactionGroupContract;

import java.time.ZonedDateTime;

public class BalanceAccountDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = GLOBAL_DATABASE_VERSION;
    public static final String DATABASE_NAME = DATABASE_NAME_VAUDITOR_DATA;

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
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        insertInitialAccounts(sqLiteDatabase);
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
