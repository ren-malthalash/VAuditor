package com.mobdev.x22.tordillo.christiandave.vauditor.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mobdev.x22.tordillo.christiandave.vauditor.R;
import com.mobdev.x22.tordillo.christiandave.vauditor.RegisterActivity;

public class LoginFragment extends Fragment {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView errorMessageTextView;
    private TextView registerLinkTextView;
    private LoginViewModel loginViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize views
        usernameEditText = rootView.findViewById(R.id.username_edittext);
        passwordEditText = rootView.findViewById(R.id.password_edittext);
        loginButton = rootView.findViewById(R.id.login_button);
        errorMessageTextView = rootView.findViewById(R.id.error_message);
        registerLinkTextView = rootView.findViewById(R.id.register_link);

        // Initialize ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Observe error message LiveData
        loginViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                showError(errorMessage);
            } else {
                errorMessageTextView.setVisibility(View.GONE);
            }
        });

        // Set click listener for the login button
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            loginViewModel.login(username, password);
        });

        // Set click listener for the register link
        registerLinkTextView.setOnClickListener(v -> {
            // Navigate to the RegisterActivity when the user clicks on "Register here"
            Intent intent = new Intent(getActivity(), RegisterActivity.class);
            startActivity(intent);
        });

        return rootView;
    }

    private void showError(String message) {
        errorMessageTextView.setVisibility(View.VISIBLE);
        errorMessageTextView.setText(message);
    }
}
