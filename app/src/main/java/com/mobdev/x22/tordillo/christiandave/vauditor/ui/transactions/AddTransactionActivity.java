package com.mobdev.x22.tordillo.christiandave.vauditor.ui.transactions;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.mobdev.x22.tordillo.christiandave.vauditor.MainActivity;
import com.mobdev.x22.tordillo.christiandave.vauditor.R;
import com.mobdev.x22.tordillo.christiandave.vauditor.VAuditorApp;
import com.mobdev.x22.tordillo.christiandave.vauditor.database.DatabaseManager;
import com.mobdev.x22.tordillo.christiandave.vauditor.databinding.ActivityAddTransactionBinding;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.transactions.TransactionGroupModel;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.transactions.TransactionModel;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;

public class AddTransactionActivity extends AppCompatActivity {

    TransactionModel transactionModel;
    TransactionGroupModel transactionGroupModel;
    ActivityAddTransactionBinding binding;
    private EditText transactionName;
    private EditText transactionAmount;
    private EditText transactionNotes;
    private Spinner toAccount;
    private Spinner fromAccount;
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
        toAccount = view.findViewById(R.id.sp_to_account);
        fromAccount = view.findViewById(R.id.sp_from_account);
        cancel = view.findViewById(R.id.btn_cancel);
        save = view.findViewById(R.id.btn_save);

        dbManager = VAuditorApp.getDatabaseManager();
        Dictionary<String, Long> balanceAccountNames = dbManager.getBalanceAccountNames();

        ArrayList<String> accountNames = Collections.list(balanceAccountNames.keys());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, new ArrayList<>(accountNames));
        toAccount.setAdapter(adapter);


        accountNames.add(0,"None");
        adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, accountNames);
        fromAccount.setAdapter(adapter);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigDecimal bdTransactionAmount = new BigDecimal(Long.parseLong(transactionAmount.getText().toString()));
                Log.d("BIG DECIMAL AMOUNT", bdTransactionAmount + "");

                long fromAccountId = 0;
                if (balanceAccountNames.get(fromAccount.getSelectedItem().toString()) != null) {
                    fromAccountId = balanceAccountNames.get(fromAccount.getSelectedItem().toString());
                }

                transactionModel = new TransactionModel(1,1,transactionName.getText().toString(), bdTransactionAmount, transactionNotes.getText().toString());
                transactionGroupModel = new TransactionGroupModel(1,
                        transactionName.getText().toString(),
                        fromAccountId,
                        balanceAccountNames.get(toAccount.getSelectedItem().toString()),
                        transactionNotes.getText().toString(),
                        ZonedDateTime.now(),
                        new ArrayList<>(Collections.singleton(transactionModel))
                );
                dbManager.insertTransaction(transactionGroupModel.generateContentValuesWithoutId(), transactionModel.generateContentValuesWithoutId());
                Intent intent = new Intent(AddTransactionActivity.this, MainActivity.class);
                startActivity(intent);
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
