package com.mobdev.x22.tordillo.christiandave.vauditor.ui.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mobdev.x22.tordillo.christiandave.vauditor.R;

import com.mobdev.x22.tordillo.christiandave.vauditor.VAuditorApp;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.notifications.NotificationModel;

import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder> {
    private final ArrayList<NotificationModel> notificationList;

    public NotificationsAdapter() {
        notificationList = VAuditorApp.getDatabaseManager().findNotifications();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationModel notification = notificationList.get(position);
        holder.bind(notification);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTextView;
        private final TextView bodyTextView;
        private final TextView dateTextView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.notificationTitle);
            bodyTextView = itemView.findViewById(R.id.notificationBody);
            dateTextView = itemView.findViewById(R.id.notificationDate);
        }

        public void bind(NotificationModel notification) {
            titleTextView.setText(notification.getNotificationTitle());
            bodyTextView.setText(notification.getNotificationBody());
            dateTextView.setText(notification.getNotifiedAt().toLocalDate().toString());
        }
    }
}
