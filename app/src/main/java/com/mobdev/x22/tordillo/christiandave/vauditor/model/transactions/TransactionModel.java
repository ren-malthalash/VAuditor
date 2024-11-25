package com.mobdev.x22.tordillo.christiandave.vauditor.model.transactions;

import android.content.ContentValues;

import com.mobdev.x22.tordillo.christiandave.vauditor.database.transactions.TransactionContract.TransactionEntry;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.BaseModel;

import java.math.BigDecimal;

public class TransactionModel extends BaseModel {
    private long transactionId;
    private long transactionGroupId;
    private String transactionName;
    private BigDecimal transactionAmount;
    private String transactionNotes;

    public TransactionModel() {}

    /** Add transaction
     * @param transactionId          Transaction ID Reference in the Database
     * @param transactionGroupId     TransactionGroupId in the Database
     * @param transactionName        Name of the Transaction
     * @param transactionAmount      Amount transferred
     * @param transactionNotes       Transaction Notes
     */
    public TransactionModel(long transactionId,
                     long transactionGroupId,
                     String transactionName,
                     BigDecimal transactionAmount,
                     String transactionNotes) {
        this.transactionId = transactionId;
        this.transactionGroupId = transactionGroupId;
        this.transactionName = transactionName;
        this.transactionAmount = transactionAmount;
        this.transactionNotes = transactionNotes;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public String getTransactionNotes() { return transactionNotes; }

    public long getTransactionGroupId() { return transactionGroupId; }

    public void setTransactionGroupId(long newGroupId) { transactionGroupId = newGroupId; }
    
    @Override
    public ContentValues generateContentValuesWithId() {
        ContentValues values = generateContentValuesWithoutId();

        values.put(TransactionEntry._ID, transactionId);
        return null;
    }

    @Override
    public ContentValues generateContentValuesWithoutId() {
        ContentValues values = new ContentValues();

        values.put(TransactionEntry.COLUMN_GROUP_ID, transactionGroupId);
        values.put(TransactionEntry.COLUMN_NAME, transactionName);
        values.put(TransactionEntry.COLUMN_AMOUNT, transactionAmount.toString());
        values.put(TransactionEntry.COLUMN_NOTES, transactionNotes);
        return values;
    }
}
