package com.mobdev.x22.tordillo.christiandave.vauditor.ui.register;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mobdev.x22.tordillo.christiandave.vauditor.database.DatabaseManager;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.balanceaccount.BalanceAccountModel;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.balanceaccount.BalanceAccountType;

import java.math.BigDecimal;

public class RegisterViewModel extends AndroidViewModel {

    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private DatabaseManager databaseManager;

    public RegisterViewModel(Application application) {
        super(application);
        databaseManager = new DatabaseManager(application);
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void register(String username, String password, String confirmPassword, int selectedAccountType) {
        // Validate inputs
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            errorMessage.setValue("All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorMessage.setValue("Passwords do not match.");
            return;
        }

        // Get the account type from selectedRadioButton
        BalanceAccountType accountType = selectedAccountType == 0
                ? BalanceAccountType.PERSON
                : BalanceAccountType.BANKING_COMPANY;

        // Create the BalanceAccountModel (user)
        BalanceAccountModel balanceAccount = new BalanceAccountModel(
                0,
                username,
                accountType,
                BigDecimal.ZERO,
                false
        );

        // Insert the balance account into the database
        long userId = databaseManager.insertBalanceAccount(balanceAccount);

        if (userId != -1) {
            errorMessage.setValue("");
        } else {
            errorMessage.setValue("Registration failed. Try again.");
        }
    }
}
