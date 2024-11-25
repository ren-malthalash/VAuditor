package com.mobdev.x22.tordillo.christiandave.vauditor.model.transactions;

import android.content.ContentValues;

import com.mobdev.x22.tordillo.christiandave.vauditor.database.transactions.TransactionGroupContract.TransactionGroupEntry;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.BaseModel;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class TransactionGroupModel extends BaseModel {
    private long transactionGroupId;
    private String transactionGroupName;
    private long transactionSenderId;
    private long transactionRecipientId;
    private String transactionGroupNote;
    private ZonedDateTime transactionGroupDate;
    private ArrayList<TransactionModel> transactions;

    TransactionGroupModel() {}

    public TransactionGroupModel(long transactionGroupId,
                                 String transactionGroupName,
                                 long transactionSenderId,
                                 long transactionRecipientId,
                                 String transactionGroupNote,
                                 ZonedDateTime transactionGroupDate,
                                 ArrayList<TransactionModel> transactions) {
        this.transactionGroupId = transactionGroupId;
        this.transactionGroupName = transactionGroupName;
        this.transactionSenderId = transactionSenderId;
        this.transactionRecipientId = transactionRecipientId;
        this.transactionGroupNote = transactionGroupNote;
        this.transactionGroupDate = transactionGroupDate;
        this.transactions = transactions;
    }

    public TransactionGroupModel(long transactionGroupId,
                                 String transactionGroupName,
                                 long transactionSenderId,
                                 long transactionRecipientId,
                                 String transactionGroupNote,
                                 ZonedDateTime transactionGroupDate) {
        this.transactionGroupId = transactionGroupId;
        this.transactionGroupName = transactionGroupName;
        this.transactionSenderId = transactionSenderId;
        this.transactionRecipientId = transactionRecipientId;
        this.transactionGroupNote = transactionGroupNote;
        this.transactionGroupDate = transactionGroupDate;
        this.transactions = new ArrayList<TransactionModel>();
    }

    public long getTransactionGroupId() {
        return transactionGroupId;
    }

    public ArrayList<TransactionModel> getTransactions() {
        return transactions;
    }

    public ArrayList<Long> getTransactionIds() {
        ArrayList<Long> ids = new ArrayList<>();
        for (TransactionModel transaction : transactions) {
            ids.add(transaction.getTransactionId());
        }
        return ids;
    }

    public String getTransactionGroupName() {
        return transactionGroupName;
    }

    public String getTransactionGroupNote() {
        return transactionGroupNote;
    }

    public ZonedDateTime getTransactionGroupDate() {
        return transactionGroupDate;
    }

    public long getTransactionSenderId() {
        return transactionSenderId;
    }

    public void setTransactionSenderId(long transactionSenderId) {
        this.transactionSenderId = transactionSenderId;
    }

    public long getTransactionRecipientId() {
        return transactionRecipientId;
    }

    public void setTransactionRecipientId(long transactionRecipientId) {
        this.transactionRecipientId = transactionRecipientId;
    }

    @Override
    public ContentValues generateContentValuesWithId() {
        ContentValues values = generateContentValuesWithoutId();

        values.put(TransactionGroupEntry._ID, transactionGroupId);
        return values;
    }

    @Override
    public ContentValues generateContentValuesWithoutId() {
        ContentValues values = new ContentValues();

        values.put(TransactionGroupEntry.COLUMN_NAME, transactionGroupName);
        values.put(TransactionGroupEntry.COLUMN_SENDER, transactionSenderId);
        values.put(TransactionGroupEntry.COLUMN_RECIPIENT, transactionRecipientId);
        values.put(TransactionGroupEntry.COLUMN_NOTES, transactionGroupNote);
        values.put(TransactionGroupEntry.COLUMN_DATE, transactionGroupDate.toString());
        return values;
    }
}
