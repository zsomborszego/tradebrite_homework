package com.tradebrite.bank.service.exceptions;

public class InvalidTransactionParameterException extends RuntimeException {

    private final String senderAccountNumber;
    private final String beneficiaryAccountNumber;

    @Override
    public String getMessage(){
        return "Account numbers cant be the same: " + senderAccountNumber + " | " + beneficiaryAccountNumber;
    }

    public InvalidTransactionParameterException(String senderAccountNumber, String beneficiaryAccountNumber){
        this.senderAccountNumber = senderAccountNumber;
        this.beneficiaryAccountNumber = beneficiaryAccountNumber;
    }

    public InvalidTransactionParameterException(String senderAccountNumber, String beneficiaryAccountNumber, Throwable cause){
        super(cause);
        this.senderAccountNumber = senderAccountNumber;
        this.beneficiaryAccountNumber = beneficiaryAccountNumber;
    }
}
