package com.mobdev.x22.tordillo.christiandave.vauditor.ui.balanceaccounts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdev.x22.tordillo.christiandave.vauditor.R;
import com.mobdev.x22.tordillo.christiandave.vauditor.ui.notifications.NotificationsAdapter;

public class BalanceAccountAdapter extends RecyclerView.Adapter <BalanceAccountAdapter.BalanceAccountViewHolder>{

    public BalanceAccountAdapter() {

    }
    @NonNull
    @Override
    public BalanceAccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_balance_accounts, parent, false);
        return new BalanceAccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

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
            accountNotes = itemView.findViewById(R.id.accountNotes);
        }
    }
}


