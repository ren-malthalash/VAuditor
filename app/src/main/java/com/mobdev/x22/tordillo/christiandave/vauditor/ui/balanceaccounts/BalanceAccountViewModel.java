package com.mobdev.x22.tordillo.christiandave.vauditor.ui.balanceaccounts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BalanceAccountViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BalanceAccountViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is transaction fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
