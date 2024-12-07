package com.mobdev.x22.tordillo.christiandave.vauditor;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.mobdev.x22.tordillo.christiandave.vauditor.ui.register.RegisterFragment;
import com.mobdev.x22.tordillo.christiandave.vauditor.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Load RegisterFragment dynamically
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new RegisterFragment())
                    .commit();
        }
    }
}
