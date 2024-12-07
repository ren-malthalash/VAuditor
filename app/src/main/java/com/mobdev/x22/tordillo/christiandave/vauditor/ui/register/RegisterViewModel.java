package com.mobdev.x22.tordillo.christiandave.vauditor.ui.register;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class RegisterViewModel extends AndroidViewModel {

    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public RegisterViewModel(Application application) {
        super(application);
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void register(String username, String password, String confirmPassword) {
        // Simple validation logic
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            errorMessage.setValue("All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorMessage.setValue("Passwords do not match.");
            return;
        }

        // Simulate a successful registration
        // You can replace this with actual network/database call
        errorMessage.setValue("");  // Clear any previous errors
        // Navigate to login or main screen if registration is successful
    }
}