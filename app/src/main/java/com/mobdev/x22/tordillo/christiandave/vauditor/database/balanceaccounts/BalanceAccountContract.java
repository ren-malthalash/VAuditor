package com.mobdev.x22.tordillo.christiandave.vauditor.database.balanceaccounts;

import android.provider.BaseColumns;

public class BalanceAccountContract {
    private BalanceAccountContract() {}

    public static class BalanceAccountEntry implements BaseColumns {
        /**
         * Balance Account - Table Name
         */
        public static final String TABLE_NAME = "balanceAccounts";
        /**
         * Balance Account - Account Name
         */
        public static final String COLUMN_NAME = "accountName";
        /**
         * Balance Account - Account Type
         */
        public static final String COLUMN_TYPE = "accountType";
        /**
         * Balance Account - Account Balance
         */
        public static final String COLUMN_BALANCE = "accountBalance";
        /**
         * Balance Account - Account Deleted
         */
        public static final String COLUMN_DELETED = "accountDeleted";
    }
}
