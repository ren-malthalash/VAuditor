package com.mobdev.x22.tordillo.christiandave.vauditor.database.notifications;

import android.provider.BaseColumns;

public class NotificationContract {
    private NotificationContract() {}

    public static class NotificationEntry implements BaseColumns {
        public static final String TABLE_NAME = "notifications";
        public static final String COLUMN_DATE = "notifiedAt";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_BODY = "body";
        public static final String COLUMN_VIEWED = "viewed";
    }
}
