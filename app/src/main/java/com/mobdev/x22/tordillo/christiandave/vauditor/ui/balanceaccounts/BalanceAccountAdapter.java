package com.mobdev.x22.tordillo.christiandave.vauditor.ui.balanceaccounts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdev.x22.tordillo.christiandave.vauditor.R;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.balanceaccount.BalanceAccountModel;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.transactions.TransactionModel;
import com.mobdev.x22.tordillo.christiandave.vauditor.ui.notifications.NotificationsAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;

public class BalanceAccountAdapter extends RecyclerView.Adapter<BalanceAccountAdapter.BalanceAccountViewHolder>{
    private ArrayList<BalanceAccountModel> accounts;

    public BalanceAccountAdapter(ArrayList<BalanceAccountModel> accounts) {
        this.accounts = accounts;
    }

    @NonNull
    @Override
    public BalanceAccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_balance_accounts, parent, false);
        return new BalanceAccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BalanceAccountViewHolder holder, int position) {
        BalanceAccountModel account = accounts.get(position);
        holder.accountName.setText(account.getBalanceAccName());
        holder.accountType.setText(account.getBalanceAccountType().toString());

        BigDecimal bdAccountBalance = new BigDecimal(String.valueOf(account.getBalanceAccountBalance()));
        holder.accountInitialBalance.setText(bdAccountBalance.toString());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class BalanceAccountViewHolder extends RecyclerView.ViewHolder {
        TextView accountName, accountType, accountInitialBalance, accountNotes;

        BalanceAccountViewHolder(@NonNull View itemView) {
            super(itemView);
            accountName = itemView.findViewById(R.id.accountName);
            accountType = itemView.findViewById(R.id.accountType);
            accountInitialBalance = itemView.findViewById(R.id.accountInitialBalance);
        }
    }
}


