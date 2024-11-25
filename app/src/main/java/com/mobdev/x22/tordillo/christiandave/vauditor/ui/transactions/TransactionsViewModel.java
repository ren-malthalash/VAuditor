package com.mobdev.x22.tordillo.christiandave.vauditor.ui.transactions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TransactionsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TransactionsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is transaction fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}