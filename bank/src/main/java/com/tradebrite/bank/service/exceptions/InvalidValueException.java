package com.tradebrite.bank.service.exceptions;

import java.math.BigDecimal;

public class InvalidValueException extends RuntimeException {

    private final BigDecimal value;

    @Override
    public String getMessage() {
        return "Value cant be less than 1 huf, your value is: " + value.toString();
    }

    public InvalidValueException(BigDecimal value){
        this.value = value;
    }

    public InvalidValueException(BigDecimal value, Throwable cause){
        super(cause);
        this.value = value;
    }
}
