package com.mobdev.x22.tordillo.christiandave.vauditor.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mobdev.x22.tordillo.christiandave.vauditor.R;
import com.mobdev.x22.tordillo.christiandave.vauditor.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView errorMessageTextView;
    private TextView registerLinkTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        usernameEditText = findViewById(R.id.username_edittext);
        passwordEditText = findViewById(R.id.password_edittext);
        loginButton = findViewById(R.id.login_button);
        errorMessageTextView = findViewById(R.id.error_message);
        registerLinkTextView = findViewById(R.id.register_link);

        // Set click listener for the login button
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Handle the login logic
            handleLogin(username, password);
        });

        // Set click listener for the register link
        registerLinkTextView.setOnClickListener(v -> {
            // Navigate to the RegisterActivity when the user clicks on "Register here"
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void handleLogin(String username, String password) {
        // Add your login logic here. For example:
        if (username.isEmpty() || password.isEmpty()) {
            showError("Both fields are required.");
            return;
        }

        // Here you can implement actual login logic (e.g., checking credentials).
        // For now, let's assume the login is successful.

        if (username.equals("test") && password.equals("password")) {
            // If login is successful, show a message and finish the activity
            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            finish();  // Finish the activity to return to the previous screen
        } else {
            // If login fails, show the error message
            showError("Invalid credentials");
        }
    }

    private void showError(String message) {
        errorMessageTextView.setVisibility(View.VISIBLE);
        errorMessageTextView.setText(message);
    }
}
