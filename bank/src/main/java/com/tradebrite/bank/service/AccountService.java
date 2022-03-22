package com.tradebrite.bank.service;
import com.tradebrite.bank.model.Account;
import com.tradebrite.bank.model.ResponseMessages;
import com.tradebrite.bank.model.entity.AccountEntity;

import java.util.List;

public interface AccountService {
    ResponseMessages registerAccount(Account account);
    List<AccountEntity> getAllAccount();
    AccountEntity findAccount(Account account);
}
