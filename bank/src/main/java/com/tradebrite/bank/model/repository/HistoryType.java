package com.tradebrite.bank.model.repository;

public enum HistoryType {
    WITHDRAW("Withdraw"),
    DEPOSIT("Deposit"),
    SENDMONEY("Send money"),
    GETMONEY("Get money");

    private String message;

    HistoryType(String typeMessage) {
        this.message = typeMessage;
    }

    public String getMessage() {
        return message;
    }
}
