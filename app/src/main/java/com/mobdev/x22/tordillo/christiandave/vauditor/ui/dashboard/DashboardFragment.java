package com.mobdev.x22.tordillo.christiandave.vauditor.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mobdev.x22.tordillo.christiandave.vauditor.R;
import com.mobdev.x22.tordillo.christiandave.vauditor.VAuditorApp;
import com.mobdev.x22.tordillo.christiandave.vauditor.databinding.FragmentDashboardBinding;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.transactions.TransactionModel;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private ArrayList<TransactionModel> recentTransactions;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recentTransactions = VAuditorApp.getDatabaseManager().getLastThreeTransactions();
        setupRecentTransactionsUI(view);
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupRecentTransactionsUI(View view) {
        TextView transactionAmount1 = view.findViewById(R.id.transactionAmount1);
        TextView transactionAmount2 = view.findViewById(R.id.transactionAmount2);
        TextView transactionAmount3 = view.findViewById(R.id.transactionAmount3);

        TextView transactionName1 = view.findViewById(R.id.transactionName1);
        TextView transactionName2 = view.findViewById(R.id.transactionName2);
        TextView transactionName3 = view.findViewById(R.id.transactionName3);

        if (!recentTransactions.isEmpty()) {
            transactionName1.setText(recentTransactions.get(0).getTransactionName());
            transactionAmount1.setText(recentTransactions.get(0).getTransactionAmount().toString());
        }
        if (recentTransactions.size() > 1) {
            transactionName2.setText(recentTransactions.get(1).getTransactionName());
            transactionAmount2.setText(recentTransactions.get(1).getTransactionAmount().toString());
        }
        if (recentTransactions.size() > 2) {
            transactionName3.setText(recentTransactions.get(2).getTransactionName());
            transactionAmount3.setText(recentTransactions.get(2).getTransactionAmount().toString());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}