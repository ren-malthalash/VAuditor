package com.mobdev.x22.tordillo.christiandave.vauditor;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.mobdev.x22.tordillo.christiandave.vauditor.databinding.ActivityMainBinding;
import com.mobdev.x22.tordillo.christiandave.vauditor.ui.notifications.NotificationsActivity;

public class MainActivity extends AppCompatActivity {

    static TextView notifCount;
    private ActivityMainBinding binding;
    private int notificationCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_transactions, R.id.navigation_account)
                .build();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        notificationCount = VAuditorApp.getDatabaseManager().getTotalUnviewedNotifications();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        // Check if the clicked item is the action_settings button
        if (id == R.id.action_main_menu_notification) {
            // Perform your action (e.g., open settings activity)
            Intent i = new Intent(MainActivity.this, NotificationsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateNotificationCount(Menu menu) {
//        MenuItem notificationItem = menu.findItem(R.id.action_main_menu_notification);

    }

    public void setNotificationCount(int count) {
        this.notificationCount = count;
        invalidateOptionsMenu(); // Refresh the menu to reflect changes
    }
}