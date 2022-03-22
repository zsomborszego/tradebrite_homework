package com.tradebrite.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AccountTransaction {

    Account account;
    BigDecimal value;

}
