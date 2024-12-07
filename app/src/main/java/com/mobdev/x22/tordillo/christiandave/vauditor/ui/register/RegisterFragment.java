package com.mobdev.x22.tordillo.christiandave.vauditor.ui.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mobdev.x22.tordillo.christiandave.vauditor.R;

public class RegisterFragment extends Fragment {

    private EditText usernameEditText, passwordEditText, confirmPasswordEditText;
    private RadioGroup accountTypeRadioGroup;
    private RegisterViewModel registerViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        // Initialize views
        usernameEditText = rootView.findViewById(R.id.username_edittext);
        passwordEditText = rootView.findViewById(R.id.password_edittext);
        confirmPasswordEditText = rootView.findViewById(R.id.confirm_password_edittext);
        accountTypeRadioGroup = rootView.findViewById(R.id.account_type_group);

        // Initialize ViewModel
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        // Register button click listener
        rootView.findViewById(R.id.register_button).setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            // Get selected account type
            int selectedAccountType = accountTypeRadioGroup.getCheckedRadioButtonId() == R.id.radio_person ? 0 : 1;

            // Register the user
            registerViewModel.register(username, password, confirmPassword, selectedAccountType);
        });

        // Observe error message
        registerViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
