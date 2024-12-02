package com.mobdev.x22.tordillo.christiandave.vauditor.ui.savings;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;

import com.mobdev.x22.tordillo.christiandave.vauditor.MainActivity;
import com.mobdev.x22.tordillo.christiandave.vauditor.R;
import com.mobdev.x22.tordillo.christiandave.vauditor.databinding.ActivityAddSavingsGoalBinding;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.transactions.TransactionModel;
import com.mobdev.x22.tordillo.christiandave.vauditor.ui.transactions.AddTransactionActivity;

import java.math.BigDecimal;

public class AddSavingsGoalActivity extends AppCompatActivity {

    ActivityAddSavingsGoalBinding binding;
    private EditText goalName;
    private EditText targetDate;
    private EditText notes;
    private Button cancel;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_savings_goal);

        binding = ActivityAddSavingsGoalBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        goalName = view.findViewById(R.id.et_goal_name);
        targetDate = view.findViewById(R.id.et_target_date);
        notes = view.findViewById(R.id.et_notes);
        cancel = view.findViewById(R.id.btn_cancel);
        save = view.findViewById(R.id.btn_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // no model yet
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSavingsGoalActivity.this, MainActivity.class);
                startActivity(intent);
            }

        });

    }
}