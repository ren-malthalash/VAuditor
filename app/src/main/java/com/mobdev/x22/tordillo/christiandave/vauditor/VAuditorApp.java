package com.mobdev.x22.tordillo.christiandave.vauditor;

import android.app.Application;
import android.util.Log;

import com.mobdev.x22.tordillo.christiandave.vauditor.database.DatabaseManager;

public class VAuditorApp extends Application {

    private static VAuditorApp instance;
    private static DatabaseManager databaseManager;

    public static DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public static VAuditorApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("APP START","CREATING TIME");
        instance = this;
        databaseManager = new DatabaseManager(getApplicationContext());
    }
}
