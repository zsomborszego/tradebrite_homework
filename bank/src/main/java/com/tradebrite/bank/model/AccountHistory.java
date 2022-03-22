package com.tradebrite.bank.model;


import com.tradebrite.bank.model.repository.HistoryType;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class AccountHistory {

    private final String type;
    private final BigDecimal value;

    public AccountHistory(HistoryType type, BigDecimal value) {
        this.type = type.getMessage();
        this.value = value;
    }
}
