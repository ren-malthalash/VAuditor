package com.mobdev.x22.tordillo.christiandave.vauditor.ui.transactions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdev.x22.tordillo.christiandave.vauditor.R;
import com.mobdev.x22.tordillo.christiandave.vauditor.VAuditorApp;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.transactions.TransactionGroupModel;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.transactions.TransactionModel;

import java.math.BigDecimal;
import java.util.ArrayList;

public class TransactionGroupAdapter extends RecyclerView.Adapter<TransactionGroupAdapter.TransactionGroupViewHolder> {
    private final ArrayList<TransactionGroupModel> transactionGroups;

    public TransactionGroupAdapter(ArrayList<TransactionGroupModel> transactionGroups) {
        this.transactionGroups = transactionGroups;
    }

    public TransactionGroupAdapter() {
        this.transactionGroups = VAuditorApp.getDatabaseManager().findTransactionGroupsWithTransactions();
    }

    @NonNull
    @Override
    public TransactionGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction_group, parent, false);
        return new TransactionGroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionGroupViewHolder holder, int position) {
        TransactionGroupModel group = transactionGroups.get(position);

        holder.transactionGroupName.setText(group.getTransactionGroupName());
        holder.transactionGroupDate.setText(group.getTransactionGroupDate().toLocalDate().toString());

        // Calculate total amount for the group
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (TransactionModel transaction : group.getTransactions()) {
            totalAmount = totalAmount.add(transaction.getTransactionAmount());
        }
        holder.transactionGroupTotal.setText("Total: ₱" + totalAmount.toString());

        // Expand/collapse functionality
        holder.itemView.setOnClickListener(view -> {
            boolean isExpanded = holder.transactionsRecyclerView.getVisibility() == View.VISIBLE;
            holder.transactionsRecyclerView.setVisibility(isExpanded ? View.GONE : View.VISIBLE);
            holder.expandIcon.setImageResource(isExpanded ? R.drawable.ic_baseline_expand_more_24 : R.drawable.ic_baseline_expand_less_24);
        });

        // Set up the inner RecyclerView for transactions
        holder.transactionsRecyclerView.setAdapter(new TransactionAdapter(group.getTransactions()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(holder.transactionsRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        holder.transactionsRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public int getItemCount() {
        return transactionGroups.size();
    }

    static class TransactionGroupViewHolder extends RecyclerView.ViewHolder {
        TextView transactionGroupName, transactionGroupDate, transactionGroupTotal;
        ImageView expandIcon;
        RecyclerView transactionsRecyclerView;

        TransactionGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionGroupName = itemView.findViewById(R.id.transactionGroupName);
            transactionGroupDate = itemView.findViewById(R.id.transactionGroupDate);
            transactionGroupTotal = itemView.findViewById(R.id.transactionGroupTotal);
            expandIcon = itemView.findViewById(R.id.expandIcon);
            transactionsRecyclerView = itemView.findViewById(R.id.transactionsRecyclerView);
            transactionsRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }
}


