package com.mobdev.x22.tordillo.christiandave.vauditor.database.notifications;

import static com.mobdev.x22.tordillo.christiandave.vauditor.database.DatabaseManager.DATABASE_NAME_VAUDITOR_DATA;
import static com.mobdev.x22.tordillo.christiandave.vauditor.database.DatabaseManager.GLOBAL_DATABASE_VERSION;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobdev.x22.tordillo.christiandave.vauditor.database.notifications.NotificationContract.NotificationEntry;

import java.time.ZonedDateTime;

public class NotificationsDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = GLOBAL_DATABASE_VERSION;
    public static final String DATABASE_NAME = DATABASE_NAME_VAUDITOR_DATA;

    private static final String SQL_CREATE_NOTIFICATION_ENTRIES =
            "CREATE TABLE " +
                    NotificationEntry.TABLE_NAME + "(" +
                    NotificationEntry._ID + " INTEGER PRIMARY KEY," +
                    NotificationEntry.COLUMN_DATE + " TEXT NOT NULL," +
                    NotificationEntry.COLUMN_TITLE + " TEXT NOT NULL," +
                    NotificationEntry.COLUMN_BODY + " TEXT NOT NULL," +
                    NotificationEntry.COLUMN_VIEWED + " TINYINT NOT NULL Default 0)";

    private static final String SQL_DELETE_NOTIFICATION_ENTRIES =
            "DROP TABLE IF EXISTS " + NotificationEntry.TABLE_NAME;

    public NotificationsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_NOTIFICATION_ENTRIES);
        insertInitialData(sqLiteDatabase);
    }

    private void insertInitialData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(NotificationEntry.COLUMN_TITLE, "WELCOME!!!");
        values.put(NotificationEntry.COLUMN_BODY, "Welcome to VAuditor!!!");
        values.put(NotificationEntry.COLUMN_DATE, ZonedDateTime.now().toString());
        values.put(NotificationEntry.COLUMN_VIEWED, false);

        db.insert(NotificationEntry.TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_NOTIFICATION_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
