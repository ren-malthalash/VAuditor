package com.mobdev.x22.tordillo.christiandave.vauditor.model.notifications;

import android.content.ContentValues;

import com.mobdev.x22.tordillo.christiandave.vauditor.database.notifications.NotificationContract.NotificationEntry;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.BaseModel;

import java.time.ZonedDateTime;

public class NotificationModel extends BaseModel {
    
    private long notifId;
    private ZonedDateTime notifiedAt;
    private String notificationTitle;
    private String notificationBody;
    private boolean notificationViewed;

    public NotificationModel(long notificationId,
                             ZonedDateTime notifiedAt,
                             String notificationTitle,
                             String notificationBody,
                             boolean notificationViewed) {
        this.notifId = notificationId;
        this.notifiedAt = notifiedAt;
        this.notificationTitle = notificationTitle;
        this.notificationBody = notificationBody;
        this.notificationViewed = notificationViewed;
    }

    public long getNotificationId() {
        return notifId;
    }

    public ZonedDateTime getNotifiedAt() {
        return notifiedAt;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public String getNotificationBody() {
        return notificationBody;
    }

    @Override
    public ContentValues generateContentValuesWithId() {
        ContentValues values = generateContentValuesWithoutId();

        values.put(NotificationEntry._ID, notifId);
        return values;
    }

    @Override
    public ContentValues generateContentValuesWithoutId() {
        ContentValues values = new ContentValues();

        values.put(NotificationEntry.COLUMN_DATE, notifiedAt.toString());
        values.put(NotificationEntry.COLUMN_TITLE, notificationTitle);
        values.put(NotificationEntry.COLUMN_BODY, notificationBody);
        values.put(NotificationEntry.COLUMN_VIEWED, notificationViewed);
        return values;
    }

    public boolean isNotificationViewed() {
        return notificationViewed;
    }
}
