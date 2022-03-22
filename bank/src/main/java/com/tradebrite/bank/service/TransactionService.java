package com.tradebrite.bank.service;

import com.tradebrite.bank.model.*;

import java.util.List;

public interface TransactionService {

    ResponseMessages depositMoneyHandler(AccountTransaction accountTransaction);
    ResponseMessages withdrawMoneyHandler(AccountTransaction accountTransaction);
    ResponseMessages transferMoneyHandler(AccountTransfer accountTransfer);
    List<List<AccountHistory>> getUserHIstory(Account account);

}
