package com.mobdev.x22.tordillo.christiandave.vauditor.ui.notifications;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdev.x22.tordillo.christiandave.vauditor.R;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.notifications.NotificationModel;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class NotificationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NotificationsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActionBar() != null) {
            getActionBar().setTitle("Notifications");
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_notification);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NotificationsAdapter();
        recyclerView.setAdapter(adapter);
    }

    // Temporary method to load notifications - replace with database retrieval as needed
//    private ArrayList<NotificationModel> loadNotifications() {
//        ArrayList<NotificationModel> notifications = new ArrayList<>();
//
//        notifications.add(new NotificationModel(1, ZonedDateTime.now(), "Title 1", "Body 1"));
//        notifications.add(new NotificationModel(2, ZonedDateTime.now(), "Title 2", "Body 2"));
//        notifications.add(new NotificationModel(3, ZonedDateTime.now(), "Title 3", "Body 3"));
//
//        return notifications;
//    }
}