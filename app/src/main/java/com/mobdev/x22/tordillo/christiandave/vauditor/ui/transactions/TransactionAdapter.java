package com.mobdev.x22.tordillo.christiandave.vauditor.ui.transactions;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdev.x22.tordillo.christiandave.vauditor.R;
import com.mobdev.x22.tordillo.christiandave.vauditor.VAuditorApp;
import com.mobdev.x22.tordillo.christiandave.vauditor.database.DatabaseManager;
import com.mobdev.x22.tordillo.christiandave.vauditor.databinding.DialogueEditTransactionBinding;
import com.mobdev.x22.tordillo.christiandave.vauditor.databinding.ItemTransactionBinding;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.transactions.TransactionModel;

import java.math.BigDecimal;
import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private ArrayList<TransactionModel> transactions;
    private static Activity activity;

    public TransactionAdapter(ArrayList<TransactionModel> transactions) {
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        TransactionModel transaction = transactions.get(position);
        holder.transactionName.setText(transaction.getTransactionName());
        holder.transactionAmount.setText("₱" + transaction.getTransactionAmount().toString());
        holder.transactionNotes.setText(transaction.getTransactionNotes());
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView transactionName, transactionAmount, transactionNotes;
        private ItemTransactionBinding transactionBinding;
        private int pos = -1;
        private TransactionModel transaction;

        TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionName = itemView.findViewById(R.id.transactionName);
            transactionAmount = itemView.findViewById(R.id.transactionAmount);
            transactionNotes = itemView.findViewById(R.id.transactionNotes);
        }

        public void bindTransactions(TransactionModel singleTransaction, int position) {
            this.pos = position;
            this.transaction = singleTransaction;

            this.transactionBinding.transactionName.setText(this.transaction.getTransactionName());
            this.transactionBinding.transactionAmount.setText(this.transaction.getTransactionAmount().toString());
            this.transactionBinding.transactionNotes.setText(this.transaction.getTransactionNotes());
        }


        private Dialog showCustomDialogue() {
            DialogueEditTransactionBinding dialogueEditTransactionBinding = DialogueEditTransactionBinding.inflate(activity.getLayoutInflater());

            dialogueEditTransactionBinding.editTransactionName.setText(this.transaction.getTransactionId() + "-" + this.transaction.getTransactionName());
            dialogueEditTransactionBinding.editTransactionAmount.setText(this.transaction.getTransactionAmount().toString());
            dialogueEditTransactionBinding.editNotes.setText(this.transaction.getTransactionNotes());

            return new AlertDialog.Builder(activity)

                    .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            DatabaseManager transactionDB = VAuditorApp.getDatabaseManager();

                            TransactionModel singleTransaction = null;
                            try {
                                singleTransaction = new TransactionModel(
                                        singleTransaction.getTransactionId(),
                                        singleTransaction.getTransactionGroupId(),
                                        singleTransaction.getTransactionName(),
                                        singleTransaction.getTransactionAmount(),
                                        singleTransaction.getTransactionNotes());

                                transactionDB.updateTransaction(singleTransaction.getTransactionId(), singleTransaction);

                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                        }})
                    // If the user presses cancel, nothing happens.
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }})
                    .create();
        }
    }
}

