package com.mobdev.x22.tordillo.christiandave.vauditor.ui.transactions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdev.x22.tordillo.christiandave.vauditor.R;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.transactions.TransactionModel;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private ArrayList<TransactionModel> transactions;

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

        TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionName = itemView.findViewById(R.id.transactionName);
            transactionAmount = itemView.findViewById(R.id.transactionAmount);
            transactionNotes = itemView.findViewById(R.id.transactionNotes);
        }
    }
}
