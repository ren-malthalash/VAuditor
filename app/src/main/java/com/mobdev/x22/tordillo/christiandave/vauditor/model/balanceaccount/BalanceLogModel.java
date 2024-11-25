package com.mobdev.x22.tordillo.christiandave.vauditor.model.balanceaccount;

import android.icu.math.BigDecimal;

public class BalanceLogModel {

    private static final BigDecimal bdZero = new BigDecimal("0");

    private String LogId;
    private String balanceAccountId;
    private BalanceLogType logType;
    private BigDecimal logAmount;
    private String transactionRef;

    BalanceLogModel(String logId, String balanceAccountId, BigDecimal logAmount, String transactionRef) {
        this.LogId = logId;
        this.balanceAccountId = balanceAccountId;
        this.logAmount = logAmount;
        if (logAmount.compareTo(bdZero) > 0) {
            this.logType = BalanceLogType.CREDIT;
        } else {
            this.logType = BalanceLogType.DEBIT;
        }
        this.transactionRef = transactionRef;
    }

    public String getLogId() {
        return LogId;
    }

    public String getBalanceAccountId() {
        return balanceAccountId;
    }

    public BalanceLogType getLogType() {
        return logType;
    }

    public BigDecimal getLogAmount() {
        return logAmount;
    }

    public String getTransactionRef() {
        return transactionRef;
    }
}
