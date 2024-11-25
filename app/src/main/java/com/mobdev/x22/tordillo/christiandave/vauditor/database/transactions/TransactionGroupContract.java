package com.mobdev.x22.tordillo.christiandave.vauditor.database.transactions;

import android.provider.BaseColumns;

public class TransactionGroupContract {
    private TransactionGroupContract() {}

    public static class TransactionGroupEntry implements BaseColumns {
        /**
         * Transaction Group - Table Name
         */
        public static final String TABLE_NAME = "transactionGroups";
        /**
         * Transaction Group - Transaction Group Sender
         */
        public static final String COLUMN_SENDER = "senderId";
        /**
         * Transaction Group - Transaction Group Recipient
         */
        public static final String COLUMN_RECIPIENT = "recipientId";
        /**
         * Transaction Group - Group Name
         */
        public static final String COLUMN_NAME = "groupName";
        /**
         * Transaction Group - Group Notes
         */
        public static final String COLUMN_NOTES = "groupNotes";
        /**
         *
         */
        public static final String COLUMN_DATE = "transactionDate";
    }
}
