package com.mobdev.x22.tordillo.christiandave.vauditor.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.mobdev.x22.tordillo.christiandave.vauditor.database.balanceaccounts.BalanceAccountContract.BalanceAccountEntry;
import com.mobdev.x22.tordillo.christiandave.vauditor.database.balanceaccounts.BalanceAccountDbHelper;
import com.mobdev.x22.tordillo.christiandave.vauditor.database.notifications.NotificationContract.NotificationEntry;
import com.mobdev.x22.tordillo.christiandave.vauditor.database.notifications.NotificationsDbHelper;
import com.mobdev.x22.tordillo.christiandave.vauditor.database.transactions.TransactionContract.TransactionEntry;
import com.mobdev.x22.tordillo.christiandave.vauditor.database.transactions.TransactionGroupContract.TransactionGroupEntry;
import com.mobdev.x22.tordillo.christiandave.vauditor.database.transactions.TransactionsDbHelper;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.balanceaccount.BalanceAccountModel;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.balanceaccount.BalanceAccountType;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.notifications.NotificationModel;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.transactions.TransactionGroupModel;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.transactions.TransactionModel;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class DatabaseManager {
    public static final int GLOBAL_DATABASE_VERSION = 12;
    public static final String DATABASE_NAME_VAUDITOR_DATA = "VAuditorData.db";
    private static final String UPDATE_WHERE_CLAUSE = "id = ?";

    private final BalanceAccountDbHelper balanceAccountDbHelper;
    private final TransactionsDbHelper transactionsDbHelper;
    private final NotificationsDbHelper notificationsDbHelper;

    public DatabaseManager(Context context) {
        transactionsDbHelper = new TransactionsDbHelper(context);
        balanceAccountDbHelper = new BalanceAccountDbHelper(context);
        notificationsDbHelper = new NotificationsDbHelper(context);
        initializeDatabases();
    }

    private void initializeDatabases() {
        balanceAccountDbHelper.getReadableDatabase();
        balanceAccountDbHelper.close();
        transactionsDbHelper.getReadableDatabase();
        transactionsDbHelper.close();
        notificationsDbHelper.getReadableDatabase();
        notificationsDbHelper.close();
    }

    public long insertBalanceAccount(BalanceAccountModel balanceAccountModel) {
        SQLiteDatabase db = balanceAccountDbHelper.getWritableDatabase();
        ContentValues values = balanceAccountModel.generateContentValuesWithoutId();
        long _id = db.insert(BalanceAccountEntry.TABLE_NAME, null, values);
        db.close();
        return _id;
    }

    public void updateBalanceAccount(long _id, ContentValues values) {
        SQLiteDatabase db = balanceAccountDbHelper.getWritableDatabase();
        db.update(BalanceAccountEntry.TABLE_NAME,
                values,
                UPDATE_WHERE_CLAUSE,
                new String[] {String.valueOf(_id)} );
        db.close();
    }

    public long insertTransaction(TransactionGroupModel transactionGroupModel,
                                  TransactionModel transactionModel) {
        SQLiteDatabase db = transactionsDbHelper.getWritableDatabase();
        ContentValues groupValues = transactionGroupModel.generateContentValuesWithoutId();
        long groupId = db.insert(TransactionGroupEntry.TABLE_NAME, null, groupValues);
        transactionModel.setTransactionGroupId(groupId);

        ContentValues transactionValues = transactionModel.generateContentValuesWithoutId();
        long transactionId = db.insert(TransactionEntry.TABLE_NAME, null, transactionValues);
        db.close();
        return transactionId;
    }

    public long insertTransaction(long transactionGroupId,
                                  TransactionModel transactionModel) {
        SQLiteDatabase db = transactionsDbHelper.getWritableDatabase();
        transactionModel.setTransactionGroupId(transactionGroupId);

        ContentValues transactionValues = transactionModel.generateContentValuesWithoutId();
        long transactionId = db.insert(TransactionEntry.TABLE_NAME, null, transactionValues);
        db.close();
        return transactionId;
    }

    public long insertTransaction(ContentValues groupValues,
                                  ContentValues transactionValues) {
        SQLiteDatabase transactionDb = transactionsDbHelper.getWritableDatabase();
        long group_id = transactionDb.insert(TransactionGroupEntry.TABLE_NAME, null, groupValues);
        transactionValues.put(TransactionEntry._ID, group_id);

        long transaction_id = transactionDb.insert(TransactionEntry.TABLE_NAME, null, transactionValues);
        transactionDb.close();
        return transaction_id;
    }

    public ArrayList<BalanceAccountModel> findBalanceAccounts() {
        SQLiteDatabase db = balanceAccountDbHelper.getWritableDatabase();
        ArrayList<BalanceAccountModel> accounts = new ArrayList<>();

        String[] columns = {
                BalanceAccountEntry._ID,
                BalanceAccountEntry.COLUMN_NAME,
                BalanceAccountEntry.COLUMN_TYPE,
                BalanceAccountEntry.COLUMN_BALANCE,
                BalanceAccountEntry.COLUMN_DELETED
        };

        String selection = BalanceAccountEntry._ID + " = ?";

        Cursor cursor = db.query(
                BalanceAccountEntry.TABLE_NAME,
                columns,
                selection,
                null,
                null,
                null,
                BalanceAccountEntry.COLUMN_NAME + " DESC"
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                boolean bDeleted = cursor.getInt(cursor.getColumnIndexOrThrow(BalanceAccountEntry.COLUMN_DELETED)) == 1;
                if (bDeleted) {
                    continue;
                }

                long id = cursor.getLong(cursor.getColumnIndexOrThrow(BalanceAccountEntry._ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(BalanceAccountEntry.COLUMN_NAME));
                BalanceAccountType accountType = BalanceAccountType.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(BalanceAccountEntry.COLUMN_TYPE)));
                BigDecimal amount = new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(BalanceAccountEntry.COLUMN_BALANCE)));

                BalanceAccountModel balanceAccount = new BalanceAccountModel(id, name, accountType, amount, false);
                accounts.add(balanceAccount);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return accounts;
    }

    public Dictionary<String, Long> getBalanceAccountNames() {
        SQLiteDatabase db = balanceAccountDbHelper.getWritableDatabase();
        Dictionary<String, Long> accountNames = new Hashtable<String, Long>() {
        };

        String[] columns = {
                BalanceAccountEntry._ID,
                BalanceAccountEntry.COLUMN_NAME,
                BalanceAccountEntry.COLUMN_DELETED
        };

        Cursor cursor = db.query(
                BalanceAccountEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                BalanceAccountEntry.COLUMN_NAME + " DESC"
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                boolean bDeleted = cursor.getInt(cursor.getColumnIndexOrThrow(BalanceAccountEntry.COLUMN_DELETED)) == 1;
                if (bDeleted) {
                    continue;
                }

                long _id = cursor.getLong(cursor.getColumnIndexOrThrow(BalanceAccountEntry._ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(BalanceAccountEntry.COLUMN_NAME));

                accountNames.put(name, _id);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return accountNames;
    }

    public ArrayList<BalanceAccountModel> findBalanceAccounts(long balanceAccountId) {
        SQLiteDatabase db = balanceAccountDbHelper.getWritableDatabase();
        ArrayList<BalanceAccountModel> accounts = new ArrayList<>();

        String[] columns = {
                BalanceAccountEntry._ID,
                BalanceAccountEntry.COLUMN_NAME,
                BalanceAccountEntry.COLUMN_TYPE,
                BalanceAccountEntry.COLUMN_BALANCE,
                BalanceAccountEntry.COLUMN_DELETED
        };

        String selection = BalanceAccountEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(balanceAccountId) };

        Cursor cursor = db.query(
                TransactionEntry.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                BalanceAccountEntry.COLUMN_NAME + " DESC"
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                boolean bDeleted = cursor.getInt(cursor.getColumnIndexOrThrow(BalanceAccountEntry.COLUMN_DELETED)) == 0;
                if (bDeleted) {
                    continue;
                }

                long id = cursor.getLong(cursor.getColumnIndexOrThrow(BalanceAccountEntry._ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(BalanceAccountEntry.COLUMN_NAME));
                BalanceAccountType accountType = BalanceAccountType.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(BalanceAccountEntry.COLUMN_TYPE)));
                BigDecimal amount = new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(BalanceAccountEntry.COLUMN_BALANCE)));

                BalanceAccountModel balanceAccount = new BalanceAccountModel(id, name, accountType, amount, false);
                accounts.add(balanceAccount);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return accounts;
    }

    /* commented this one out
    public void updateTransaction(long _id, ContentValues values) {
        SQLiteDatabase db = balanceAccountDbHelper.getWritableDatabase();
        db.update(TransactionEntry.TABLE_NAME,
                values,
                UPDATE_WHERE_CLAUSE,
                new String[] {String.valueOf(_id)} );
        db.close();
    }*/

    public void updateTransaction(long _id, TransactionModel transactionModel) {
        SQLiteDatabase db = balanceAccountDbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(TransactionEntry.COLUMN_GROUP_ID, transactionModel.getTransactionGroupId());
        contentValues.put(TransactionEntry.COLUMN_NAME, transactionModel.getTransactionName());
        contentValues.put(TransactionEntry.COLUMN_AMOUNT, transactionModel.getTransactionAmount().toString());
        contentValues.put(TransactionEntry.COLUMN_NOTES, transactionModel.getTransactionNotes());

        db.update(TransactionEntry.TABLE_NAME,
                contentValues,
                UPDATE_WHERE_CLAUSE,
                new String[]{String.valueOf(_id)});
        db.close();
    }

    public ArrayList<Long> insertTransactions(TransactionGroupModel transactionGroupModel,
                                   ArrayList<TransactionModel> transactionModels) {
        SQLiteDatabase db = transactionsDbHelper.getWritableDatabase();
        ContentValues groupValues = transactionGroupModel.generateContentValuesWithoutId();
        long groupId = db.insert(TransactionGroupEntry.TABLE_NAME, null, groupValues);
        db.close();
        ArrayList<Long> transactionIds = new ArrayList<>();
        for (TransactionModel transactionModel: transactionModels) {
            transactionIds.add(insertTransaction(groupId, transactionModel));
        }
        return transactionIds;
    }

    public ArrayList<TransactionGroupModel> findTransactionGroupsWithTransactions() {
        SQLiteDatabase db = transactionsDbHelper.getWritableDatabase();
        ArrayList<TransactionGroupModel> transactionGroups = new ArrayList<>();

        String[] columns = {
                TransactionGroupEntry._ID,
                TransactionGroupEntry.COLUMN_NAME,
                TransactionGroupEntry.COLUMN_SENDER,
                TransactionGroupEntry.COLUMN_RECIPIENT,
                TransactionGroupEntry.COLUMN_NOTES,
                TransactionGroupEntry.COLUMN_DATE
        };

        Cursor cursor = db.query(
                TransactionGroupEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                TransactionGroupEntry.COLUMN_DATE + " DESC"
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                long _id = cursor.getLong(cursor.getColumnIndexOrThrow(TransactionGroupEntry._ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(TransactionGroupEntry.COLUMN_NAME));
                long senderId = cursor.getLong(cursor.getColumnIndexOrThrow(TransactionGroupEntry.COLUMN_SENDER));
                long recipientId = cursor.getLong(cursor.getColumnIndexOrThrow(TransactionGroupEntry.COLUMN_RECIPIENT));
                String note = cursor.getString(cursor.getColumnIndexOrThrow(TransactionGroupEntry.COLUMN_NOTES));
                ZonedDateTime date = ZonedDateTime.parse(cursor.getString(cursor.getColumnIndexOrThrow(TransactionGroupEntry.COLUMN_DATE)));

                ArrayList<TransactionModel> transactions = findTransactions(_id);
                TransactionGroupModel group = new TransactionGroupModel(_id, name, senderId, recipientId, note, date, new ArrayList<>(transactions));
                transactionGroups.add(group);

            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return transactionGroups;
    }

    public ArrayList<TransactionModel> findTransactions(long transactionGroupId) {
        SQLiteDatabase db = transactionsDbHelper.getWritableDatabase();
        ArrayList<TransactionModel> transactions = new ArrayList<>();

        String[] columns = {
                TransactionEntry._ID,
                TransactionEntry.COLUMN_GROUP_ID,
                TransactionEntry.COLUMN_NAME,
                TransactionEntry.COLUMN_AMOUNT,
                TransactionEntry.COLUMN_NOTES
        };

        String selection = TransactionEntry.COLUMN_GROUP_ID + " = ?";
        String[] selectionArgs = { String.valueOf(transactionGroupId) };

        Cursor cursor = db.query(
                TransactionEntry.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(TransactionEntry._ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(TransactionEntry.COLUMN_NAME));
                BigDecimal amount = new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(TransactionEntry.COLUMN_AMOUNT)));
                String notes = cursor.getString(cursor.getColumnIndexOrThrow(TransactionEntry.COLUMN_NOTES));

                TransactionModel transaction = new TransactionModel(id, transactionGroupId, name, amount, notes);
                transactions.add(transaction);

            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return transactions;
    }


    public void updateTransactionGroup(long _id, ContentValues values) {
        SQLiteDatabase db = balanceAccountDbHelper.getWritableDatabase();
        db.update(TransactionGroupEntry.TABLE_NAME,
                values,
                UPDATE_WHERE_CLAUSE,
                new String[] {String.valueOf(_id)} );
        db.close();
    }

    public long insertNotification(NotificationModel notificationModel) {
        SQLiteDatabase db = notificationsDbHelper.getWritableDatabase();
        ContentValues values = notificationModel.generateContentValuesWithoutId();
        long _id = db.insert(NotificationEntry.TABLE_NAME, null, values);
        db.close();
        return _id;
    }

    public void updateNotifications(long _id, ContentValues values) {
        SQLiteDatabase db = notificationsDbHelper.getWritableDatabase();
        db.update(NotificationEntry.TABLE_NAME,
                values,
                UPDATE_WHERE_CLAUSE,
                new String[] {String.valueOf(_id)} );
        db.close();
    }

    public ArrayList<NotificationModel> findNotifications() {
        ArrayList<NotificationModel> notifications = new ArrayList<>();
        SQLiteDatabase db = notificationsDbHelper.getReadableDatabase();

        String[] columns = {
                NotificationEntry._ID,
                NotificationEntry.COLUMN_DATE,
                NotificationEntry.COLUMN_TITLE,
                NotificationEntry.COLUMN_BODY,
                NotificationEntry.COLUMN_VIEWED
        };

        Cursor cursor = db.query(
                NotificationEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                NotificationEntry.COLUMN_DATE + " DESC"
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(NotificationEntry._ID));
                String dateStr = cursor.getString(cursor.getColumnIndexOrThrow(NotificationEntry.COLUMN_DATE));
                ZonedDateTime notifiedAt = ZonedDateTime.parse(dateStr);
                String title = cursor.getString(cursor.getColumnIndexOrThrow(NotificationEntry.COLUMN_TITLE));
                String body = cursor.getString(cursor.getColumnIndexOrThrow(NotificationEntry.COLUMN_BODY));
                boolean viewed = cursor.getInt(cursor.getColumnIndexOrThrow(NotificationEntry.COLUMN_VIEWED)) == 1;

                notifications.add(new NotificationModel(id, notifiedAt, title, body, viewed));
            } while (cursor.moveToNext());

            cursor.close();
        }
        db.close();
        return notifications;
    }

    public int getTotalUnviewedNotifications() {
        SQLiteDatabase db = notificationsDbHelper.getReadableDatabase();
        final String selection = NotificationEntry.COLUMN_VIEWED + " = ?";
        final String[] selectionArgs = {"0"};
        final String[] columns = {
                NotificationEntry._ID,
                NotificationEntry.COLUMN_DATE,
                NotificationEntry.COLUMN_TITLE,
                NotificationEntry.COLUMN_BODY
        };

        Cursor cursor = db.query(
                NotificationEntry.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                NotificationEntry.COLUMN_DATE + " DESC"
        );
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public ArrayList<TransactionModel> getLastThreeTransactions() {
        ArrayList<TransactionModel> transactions = new ArrayList<>();
        SQLiteDatabase db = transactionsDbHelper.getReadableDatabase();

        String query = "SELECT T.*, TG." + TransactionGroupEntry.COLUMN_DATE +
                " FROM " + TransactionEntry.TABLE_NAME + " T" +
                " JOIN " + TransactionGroupEntry.TABLE_NAME + " TG" +
                " ON T." + TransactionEntry.COLUMN_GROUP_ID + " = TG." + TransactionGroupEntry._ID +
                " ORDER BY TG." + TransactionGroupEntry.COLUMN_DATE + " DESC LIMIT 3";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(TransactionEntry._ID));
                long groupId = cursor.getLong(cursor.getColumnIndexOrThrow(TransactionEntry.COLUMN_GROUP_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(TransactionEntry.COLUMN_NAME));
                BigDecimal amount = new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(TransactionEntry.COLUMN_AMOUNT)));
                String notes = cursor.getString(cursor.getColumnIndexOrThrow(TransactionEntry.COLUMN_NOTES));

                // Create a TransactionModel object with the retrieved data
                TransactionModel transaction = new TransactionModel(id, groupId, name, amount, notes);
                transactions.add(transaction);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return transactions;
    }
}
