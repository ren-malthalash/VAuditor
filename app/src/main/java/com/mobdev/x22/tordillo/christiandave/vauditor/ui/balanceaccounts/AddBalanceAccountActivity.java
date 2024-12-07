package com.mobdev.x22.tordillo.christiandave.vauditor.ui.balanceaccounts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mobdev.x22.tordillo.christiandave.vauditor.MainActivity;
import com.mobdev.x22.tordillo.christiandave.vauditor.R;
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
    private Spinner accountType;
    private EditText initialBalance;
    private EditText notes;
    private Button cancel;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance_account);

        binding = ActivityAddBalanceAccountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        accountName = view.findViewById(R.id.et_account_name);
        accountType = view.findViewById(R.id.sp_account_type);
        initialBalance = view.findViewById(R.id.et_initial_balance);
        notes = view.findViewById(R.id.et_notes);
        cancel = view.findViewById(R.id.btn_cancel);
        save = view.findViewById(R.id.btn_save);

        String[] accountTypes = new String[]{"Banking Company", "Person"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, accountTypes);
        accountType.setAdapter(adapter);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigDecimal bdAccountBalance = new BigDecimal(Long.parseLong(initialBalance.getText().toString()));
                balanceAccountModel = new BalanceAccountModel(1, accountName.getText().toString(), BalanceAccountType.values()[accountType.getSelectedItemPosition()], bdAccountBalance, false);
                balanceAccountModel.generateContentValuesWithoutId();
                Intent intent = new Intent(AddBalanceAccountActivity.this, MainActivity.class);
                startActivity(intent);
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
