package com.mobdev.x22.tordillo.christiandave.vauditor.ui.transactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdev.x22.tordillo.christiandave.vauditor.R;
import com.mobdev.x22.tordillo.christiandave.vauditor.databinding.FragmentTransactionsBinding;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.transactions.TransactionGroupModel;

import java.util.ArrayList;
import java.util.List;

public class TransactionsFragment extends Fragment {
    private RecyclerView recyclerView;
    private TransactionGroupAdapter adapter;
    private ArrayList<TransactionGroupModel> transactionGroups;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction_group, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewTransactionGroups);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new TransactionGroupAdapter();
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }
}
