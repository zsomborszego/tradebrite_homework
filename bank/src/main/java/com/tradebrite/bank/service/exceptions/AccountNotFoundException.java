package com.tradebrite.bank.service.exceptions;

public class AccountNotFoundException extends RuntimeException {

    private final String accountNumber;

    @Override
    public String getMessage() {
        return "Wrong account number: " + accountNumber;
    }

    public AccountNotFoundException(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    public AccountNotFoundException(String accountNumber, Throwable cause) {
        super(cause);
        this.accountNumber = accountNumber;
    }

}
