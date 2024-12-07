package com.mobdev.x22.tordillo.christiandave.vauditor.ui.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mobdev.x22.tordillo.christiandave.vauditor.R;

public class RegisterFragment extends Fragment {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button registerButton;
    private TextView errorMessageTextView;
    private RegisterViewModel registerViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        // Initialize views
        usernameEditText = rootView.findViewById(R.id.username_edittext);
        passwordEditText = rootView.findViewById(R.id.password_edittext);
        confirmPasswordEditText = rootView.findViewById(R.id.confirm_password_edittext);
        registerButton = rootView.findViewById(R.id.register_button);
        errorMessageTextView = rootView.findViewById(R.id.error_message);

        // Initialize ViewModel
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        // Observe error message LiveData
        registerViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                showError(errorMessage);
            } else {
                errorMessageTextView.setVisibility(View.GONE);
            }
        });
        // Set click listener for the register button
        registerButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();
            registerViewModel.register(username, password, confirmPassword);
        });

        return rootView;
    }

    private void showError(String message) {
        errorMessageTextView.setVisibility(View.VISIBLE);
        errorMessageTextView.setText(message);
    }
}