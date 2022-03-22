package com.tradebrite.bank.service.exceptions;

public class InvalidWithdrawException extends RuntimeException {


    @Override
    public String getMessage(){
        return "Account don't have enogh money for withdraw";
    }

    public InvalidWithdrawException(){
    }

    public InvalidWithdrawException(Throwable cause){
        super(cause);
    }
}
