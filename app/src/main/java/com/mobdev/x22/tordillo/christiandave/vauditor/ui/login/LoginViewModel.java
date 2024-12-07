package com.mobdev.x22.tordillo.christiandave.vauditor.ui.login;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // Simulate login
    public void login(String username, String password) {
        // For now, we'll simulate a simple login check
        if (username.isEmpty() || password.isEmpty()) {
            errorMessage.setValue("Please enter both username and password.");
        } else if (username.equals("admin") && password.equals("password")) {
            // Simulate successful login
            errorMessage.setValue(""); // Clear any previous error messages
            // Handle successful login (e.g., navigate to another screen)
        } else {
            // Invalid credentials
            errorMessage.setValue("Invalid username or password.");
        }
    }
}
