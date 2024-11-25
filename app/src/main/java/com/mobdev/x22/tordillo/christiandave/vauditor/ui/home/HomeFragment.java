package com.mobdev.x22.tordillo.christiandave.vauditor.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mobdev.x22.tordillo.christiandave.vauditor.R;
import com.mobdev.x22.tordillo.christiandave.vauditor.databinding.FragmentHomeBinding;
import com.mobdev.x22.tordillo.christiandave.vauditor.ui.balanceaccounts.AddBalanceAccountActivity;
import com.mobdev.x22.tordillo.christiandave.vauditor.ui.savings.AddSavingsGoalActivity;
import com.mobdev.x22.tordillo.christiandave.vauditor.ui.transactions.AddTransactionActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private LinearLayout transactionBox, balanceAccountBox, savingsGoalBox;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        transactionBox = root.findViewById(R.id.btnAddTransaction);
        balanceAccountBox = root.findViewById(R.id.btnAddBalanceAccount);
        savingsGoalBox = root.findViewById(R.id.btnAddSavingsGoal);
        setupBoxListeners();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupBoxListeners() {
        // Set OnClickListener for the transaction box
        transactionBox.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddTransactionActivity.class);
            startActivity(intent);
        });

        // Set OnClickListener for the balance account box
        balanceAccountBox.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddBalanceAccountActivity.class);
            startActivity(intent);
        });

        // Set OnClickListener for the savings goal box
        savingsGoalBox.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddSavingsGoalActivity.class);
            startActivity(intent);
        });
    }
}