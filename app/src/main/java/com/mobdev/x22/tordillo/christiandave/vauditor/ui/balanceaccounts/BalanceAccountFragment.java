package com.mobdev.x22.tordillo.christiandave.vauditor.ui.balanceaccounts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mobdev.x22.tordillo.christiandave.vauditor.databinding.FragmentBalanceAccountBinding;

public class BalanceAccountFragment extends Fragment {

    private FragmentBalanceAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BalanceAccountViewModel balanceAccountViewModel =
                new ViewModelProvider(this).get(BalanceAccountViewModel.class);

        binding = FragmentBalanceAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textBalanceaccount;
        balanceAccountViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
