package com.mobdev.x22.tordillo.christiandave.vauditor.ui.transactions;

import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.mobdev.x22.tordillo.christiandave.vauditor.MainActivity;
import com.mobdev.x22.tordillo.christiandave.vauditor.R;
import com.mobdev.x22.tordillo.christiandave.vauditor.database.DatabaseManager;
import com.mobdev.x22.tordillo.christiandave.vauditor.databinding.ActivityAddTransactionBinding;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.transactions.TransactionModel;

import java.math.BigDecimal;

public class AddTransactionActivity extends AppCompatActivity {

    TransactionModel transactionModel;
    ActivityAddTransactionBinding binding;
    private EditText transactionName;
    private EditText transactionAmount;
    private EditText transactionNotes;
    private Button cancel;
    private Button save;
    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddTransactionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        transactionName = view.findViewById(R.id.et_transaction_name);
        transactionAmount = view.findViewById(R.id.et_transaction_amount);
        transactionNotes = view.findViewById(R.id.et_transaction_notes);
        cancel = view.findViewById(R.id.btn_cancel);
        save = view.findViewById(R.id.btn_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigDecimal bdTransactionAmount = new BigDecimal(Long.parseLong(transactionAmount.getText().toString()));
                // transactionId and transactionGroupId has a value of 1 as idk how to increment it properly
                transactionModel = new TransactionModel(1,1,transactionName.getText().toString(), bdTransactionAmount, transactionNotes.getText().toString());
                dbManager = new DatabaseManager(getApplicationContext());
                dbManager.insertTransaction(transactionModel.getTransactionGroupId(), transactionModel);
            }

        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTransactionActivity.this, MainActivity.class);
                startActivity(intent);
            }

        });

    }
}
