package com.tradebrite.bank.service;

import com.tradebrite.bank.model.Account;
import com.tradebrite.bank.model.ResponseMessages;
import com.tradebrite.bank.model.entity.AccountEntity;
import com.tradebrite.bank.model.repository.AccountRepository;
import com.tradebrite.bank.service.exceptions.AccountAlreadyExistException;
import com.tradebrite.bank.service.exceptions.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService{


    private final AccountRepository accountRepository;
    private final Random rand;

    @Override
    public ResponseMessages registerAccount(Account account){
        account.setAccountBalance(BigDecimal.valueOf(0));
        account.setAccountNumber(generateAccountnumber());
        account.setRegistrationTime(LocalDateTime.now());
        AccountEntity accountEntity = new AccountEntity(account);
        try {
            accountRepository.save(accountEntity);
            log.info("Register new account in the db name: {}", accountEntity.getName());
            return ResponseMessages.SUCCESSFULSAVEACCOUNT;
        }catch (Exception e){
            throw new AccountAlreadyExistException();
        }
    }

    @Override
    public List<AccountEntity> getAllAccount() {
        return accountRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }


    private String generateAccountnumber(){
        StringBuilder accountNumber = new StringBuilder();
        byte low = 0;
        byte high = 9;
        for (int i = 0; i < 16; i++) {
            byte generateValue = (byte) (rand.nextInt(high-low) + low);
            accountNumber.append(String.valueOf(generateValue));
            if (i == 7){
                accountNumber.append("-");
            }
        }
        return accountNumber.toString();
    }

    public AccountEntity findAccount(Account account){
        String accountNumber = account.getAccountNumber();
        return accountRepository.findByAccountNumberEquals(accountNumber).orElseThrow(()-> new AccountNotFoundException(accountNumber));
    }

}
