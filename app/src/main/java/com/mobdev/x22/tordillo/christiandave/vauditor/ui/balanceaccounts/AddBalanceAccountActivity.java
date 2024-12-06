package com.mobdev.x22.tordillo.christiandave.vauditor.ui.balanceaccounts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mobdev.x22.tordillo.christiandave.vauditor.MainActivity;
import com.mobdev.x22.tordillo.christiandave.vauditor.R;
import com.mobdev.x22.tordillo.christiandave.vauditor.database.DatabaseManager;
import com.mobdev.x22.tordillo.christiandave.vauditor.databinding.ActivityAddBalanceAccountBinding;
import com.mobdev.x22.tordillo.christiandave.vauditor.databinding.ActivityAddTransactionBinding;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.balanceaccount.BalanceAccountModel;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.balanceaccount.BalanceAccountType;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.transactions.TransactionModel;
import com.mobdev.x22.tordillo.christiandave.vauditor.ui.transactions.AddTransactionActivity;

import java.math.BigDecimal;

public class AddBalanceAccountActivity extends AppCompatActivity {

    BalanceAccountModel balanceAccountModel;
    ActivityAddBalanceAccountBinding binding;
    private EditText accountName;
    private EditText accountType;
    private EditText initialBalance;
    private EditText notes;
    private Button cancel;
    private Button save;
    DatabaseManager dbManager;
    private BalanceAccountType type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance_account);

        binding = ActivityAddBalanceAccountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        accountName = view.findViewById(R.id.et_account_name);
        accountType = view.findViewById(R.id.spinner_account_type);
        initialBalance = view.findViewById(R.id.et_initial_balance);
        notes = view.findViewById(R.id.et_notes);
        cancel = view.findViewById(R.id.btn_cancel);
        save = view.findViewById(R.id.btn_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigDecimal bdAccountBalance = new BigDecimal(Long.parseLong(initialBalance.getText().toString()));

                String sType = accountType.toString();
                if (sType.equals("Banking Company")) {
                    type = BalanceAccountType.BANKING_COMPANY;
                } else {
                    type = BalanceAccountType.PERSON;
                }

                // balanceAccId has a value of 1 as idk how to increment it properly
                balanceAccountModel = new BalanceAccountModel(1, accountName.getText().toString(), type, bdAccountBalance, false);
                dbManager = new DatabaseManager(getApplicationContext());
                dbManager.insertBalanceAccount(balanceAccountModel);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddBalanceAccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
