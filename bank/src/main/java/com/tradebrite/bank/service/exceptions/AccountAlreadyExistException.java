package com.tradebrite.bank.service.exceptions;

public class AccountAlreadyExistException extends RuntimeException {


    @Override
    public String getMessage() {
        return "This account already exist, please change user details";

    }

    public AccountAlreadyExistException() {
    }


    public AccountAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
