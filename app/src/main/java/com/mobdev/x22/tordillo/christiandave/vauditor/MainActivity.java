package com.mobdev.x22.tordillo.christiandave.vauditor;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.mobdev.x22.tordillo.christiandave.vauditor.ui.login.LoginActivity;
import com.mobdev.x22.tordillo.christiandave.vauditor.ui.login.LoginFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobdev.x22.tordillo.christiandave.vauditor.databinding.ActivityMainBinding;
import com.mobdev.x22.tordillo.christiandave.vauditor.ui.notifications.NotificationsActivity;
import com.mobdev.x22.tordillo.christiandave.vauditor.ui.useraccount.AccountFragment;

public class MainActivity extends AppCompatActivity {

    private TextView notifCount;
    private ActivityMainBinding binding;
    private int notificationCount = 0;

    private FloatingActionButton fabVoiceRecognition;

    private static SpeechRecognizer speechRecognizer;

    private static Resources resources;

    private VoiceCommandRecognition voiceCommandRecognition;

    boolean bSpeechOn;

    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "user_session";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USERNAME = "username";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Check login status
        boolean isLoggedIn = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
        if (!isLoggedIn) {
            Toast.makeText(this, "You are not logged in.", Toast.LENGTH_SHORT).show();
        } else {
            String username = sharedPreferences.getString(KEY_USERNAME, "Guest");
            Toast.makeText(this, "Welcome back, " + username, Toast.LENGTH_SHORT).show();
        }

        fabVoiceRecognition = findViewById(R.id.fabVoiceRecognition);
        bSpeechOn = false;

        resources = getResources();

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
        NavigationUI.setupWithNavController(navView, navController);

        navView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            // Check if the account button was clicked
            if (id == R.id.navigation_account) {
                // Check if the user is logged in
                if (!isLoggedIn) {
                    // If not logged in, prompt the user to log in
                    Toast.makeText(MainActivity.this, "Please log in to access your account.", Toast.LENGTH_SHORT).show();

                    // Replace the current fragment with the LoginFragment
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    // If logged in, navigate to the account page
                    Intent intent = new Intent(MainActivity.this, AccountFragment.class); // Use AccountActivity or desired activity
                    startActivity(intent);
                    return true; // Allow navigation to the account page
                }
            }



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        //.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        Intent speechRecognizeIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                .putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, true)
                .putExtra(RecognizerIntent.LANGUAGE_MODEL_FREE_FORM, true)
                .putExtra(RecognizerIntent.EXTRA_PROMPT, true);

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        voiceCommandRecognition = new VoiceCommandRecognition(this, fabVoiceRecognition, layout);

        speechRecognizer.setRecognitionListener(voiceCommandRecognition);

        fabVoiceRecognition.setOnClickListener(view -> {
            if (!voiceCommandRecognition.isSpeechOn()) {
                //Start listening
                fabVoiceRecognition.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.red_700)));

                speechRecognizer.startListening(speechRecognizeIntent);
                voiceCommandRecognition.setSpeechOn(true);
            } else {
                //Stop listening
                fabVoiceRecognition.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.purple_500)));

                speechRecognizer.stopListening();
                voiceCommandRecognition.setSpeechOn(false);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
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
        MenuItem notificationItem = menu.findItem(R.id.action_main_menu_notification);
    }

    public void setNotificationCount(int count){
        this.notificationCount = count;
        invalidateOptionsMenu(); // Refresh the menu to reflect changes
    }
}