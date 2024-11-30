package com.mobdev.x22.tordillo.christiandave.vauditor.model.balanceaccount;

import android.content.ContentValues;

import com.mobdev.x22.tordillo.christiandave.vauditor.database.balanceaccounts.BalanceAccountContract.BalanceAccountEntry;
import com.mobdev.x22.tordillo.christiandave.vauditor.model.BaseModel;

import java.math.BigDecimal;

public class BalanceAccountModel extends BaseModel {
    private long balanceAccId;
    private String balanceAccName;
    private BalanceAccountType balanceAccType;
    private BigDecimal balanceAccBalance;
    private boolean balanceAccountDeleted;

    BalanceAccountModel() {}

    public BalanceAccountModel(long balanceAccId,
                               String balanceAccName,
                               BalanceAccountType balanceAccType,
                               BigDecimal balanceAccBalance,
                               boolean balanceAccountDeleted) {
        this.balanceAccId = balanceAccId;
        this.balanceAccName = balanceAccName;
        this.balanceAccType = balanceAccType;
        this.balanceAccBalance = balanceAccBalance;
        this.balanceAccountDeleted = balanceAccountDeleted;
    }

    public long getBalanceAccountId() {
        return balanceAccId;
    }

    public BalanceAccountType getBalanceAccountType() {
        return balanceAccType;
    }

    public BigDecimal getBalanceAccountBalance() {
        return balanceAccBalance;
    }

    public boolean isBalanceAccountDeleted() {
        return balanceAccountDeleted;
    }

    public String getBalanceAccName() {
        return balanceAccName;
    }

    @Override
    public ContentValues generateContentValuesWithId() {
        ContentValues values = generateContentValuesWithoutId();

        values.put(BalanceAccountEntry._ID, balanceAccId);
        return values;
    }

    @Override
    public ContentValues generateContentValuesWithoutId() {
        ContentValues values = new ContentValues();

        values.put(BalanceAccountEntry.COLUMN_NAME, balanceAccName);
        values.put(BalanceAccountEntry.COLUMN_TYPE, balanceAccType.ordinal());
        values.put(BalanceAccountEntry.COLUMN_BALANCE, balanceAccBalance.toString());
        values.put(BalanceAccountEntry.COLUMN_DELETED, balanceAccountDeleted);
        return values;
    }
}
