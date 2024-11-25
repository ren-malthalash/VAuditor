package com.mobdev.x22.tordillo.christiandave.vauditor.database.transactions;

import android.provider.BaseColumns;

public class TransactionContract {
    private TransactionContract() {}

    public static class TransactionEntry implements BaseColumns {
        public static final String TABLE_NAME = "transactions";
        public static final String COLUMN_GROUP_ID = "transactionGroupId";
        public static final String COLUMN_NAME = "transactionName";
        //public static final String COLUMN_SENDER = "senderId";
        public static final String COLUMN_AMOUNT = "transactionAmount";
        //public static final String COLUMN_RECIPIENT = "recipientId";
        public static final String COLUMN_NOTES = "transactionNotes";
    }
}
