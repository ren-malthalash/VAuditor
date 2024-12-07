package com.mobdev.x22.tordillo.christiandave.vauditor;

import android.content.ContentValues;

import com.mobdev.x22.tordillo.christiandave.vauditor.database.DatabaseManager;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.notifications.NotificationModel;

import java.util.ArrayList;

public class NotificationsManager {

    private final DatabaseManager databaseManager;

    public NotificationsManager() {
        // Get the DatabaseManager instance from VAuditorApp
        this.databaseManager = VAuditorApp.getDatabaseManager();
    }

    // Fetch all notifications from the database
    public ArrayList<NotificationModel> getAllNotifications() {
        return databaseManager.findNotifications();
    }

    // Add a new notification to the database
    public long addNotification(NotificationModel notification) {
        return databaseManager.insertNotification(notification);
    }

    // Delete a notification by ID (Not explicitly provided in DatabaseManager, requires extension)
    public void deleteNotification(long notificationId) {
        ContentValues values = new ContentValues();
        values.put("deleted", 1); // Assuming a "deleted" column for soft deletes
        databaseManager.updateNotifications(notificationId, values);
    }

    // Mark a notification as read
    public void markAsRead(long notificationId) {
        ContentValues values = new ContentValues();
        values.put("viewed", 1); // Assuming "viewed" column is 0 (unread) or 1 (read)
        databaseManager.updateNotifications(notificationId, values);
    }

    // Get the count of unviewed notifications
    public int getTotalUnviewedNotifications() {
        return databaseManager.getTotalUnviewedNotifications();
    }
}
