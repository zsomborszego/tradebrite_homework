package com.tradebrite.bank.model;

public enum ResponseMessages {
    SUCCESSFULSAVEACCOUNT("User successfully saved"),
    SUCCESSFULDEPOSIT("Deposit was successful"),
    SUCCESSFULWITHDRAW("Withdraw was successful"),
    SUCCESSFULTRANSFER("Transfer was successful");


    private final String statusMessage;

    ResponseMessages(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
