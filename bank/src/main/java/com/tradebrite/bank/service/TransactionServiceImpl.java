package com.tradebrite.bank.service;

import com.tradebrite.bank.model.*;
import com.tradebrite.bank.model.entity.AccountEntity;
import com.tradebrite.bank.model.entity.AccountTransferEntity;
import com.tradebrite.bank.model.entity.AccountValueFlowEntity;
import com.tradebrite.bank.model.repository.AccountRepository;
import com.tradebrite.bank.model.repository.AccountValueFlowRepository;
import com.tradebrite.bank.model.repository.HistoryType;
import com.tradebrite.bank.model.repository.TransferRepository;
import com.tradebrite.bank.service.exceptions.AccountNotFoundException;
import com.tradebrite.bank.service.exceptions.InvalidTransactionParameterException;
import com.tradebrite.bank.service.exceptions.InvalidValueException;
import com.tradebrite.bank.service.exceptions.InvalidWithdrawException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;
    private final AccountValueFlowRepository accountValueFlowRepository;

    @Override
    @Transactional
    public ResponseMessages depositMoneyHandler(AccountTransaction accountTransaction) {
        String accountNumber = accountTransaction.getAccount().getAccountNumber();
        BigDecimal value = accountTransaction.getValue();
        checkTransactionValue(value);
        AccountEntity accountEntity = getAccountEntity(accountNumber);
        accountEntity = makeAccountDepositValue(accountEntity, value);
        saveAccountCashFlowHistory(accountEntity, value);
        log.info("User account {}, deposit {} value", accountNumber, value);
        return ResponseMessages.SUCCESSFULDEPOSIT;
    }

    @Override
    @Transactional
    public ResponseMessages withdrawMoneyHandler(AccountTransaction accountTransaction) {
        String accountNumber = accountTransaction.getAccount().getAccountNumber();
        BigDecimal value = accountTransaction.getValue();
        checkTransactionValue(value);
        AccountEntity accountEntity = getAccountEntity(accountNumber);
        accountEntity = makeAccountWithdrawValue(accountEntity, value);
        saveAccountCashFlowHistory(accountEntity, value.negate());
        log.info("User account {}, withdraw {} value", accountNumber, value);
        return ResponseMessages.SUCCESSFULWITHDRAW;
    }

    @Override
    @Transactional
    public ResponseMessages transferMoneyHandler(AccountTransfer accountTransfer) {
        String message = accountTransfer.getMessage();
        String senderAccountNumber = accountTransfer.getSender().getAccountNumber();
        String beneficiaryAccountNumber = accountTransfer.getBeneficiary().getAccountNumber();
        BigDecimal value = accountTransfer.getValue();
        checkTransactionValue(value);
        checkTransactionAccountNotSame(senderAccountNumber, beneficiaryAccountNumber);
        AccountEntity senderAccountEntity = getAccountEntity(senderAccountNumber);
        AccountEntity beneficiaryAccountEntity = getAccountEntity(beneficiaryAccountNumber);
        senderAccountEntity = makeAccountWithdrawValue(senderAccountEntity,value);
        beneficiaryAccountEntity = makeAccountDepositValue(beneficiaryAccountEntity, value);
        saveAccountTransferHistory(senderAccountEntity,beneficiaryAccountEntity,value.negate(),message);
        log.info("User: {} tranfer to: {} money: {}", senderAccountNumber, beneficiaryAccountNumber,value);
        return ResponseMessages.SUCCESSFULTRANSFER;
    }

    private AccountEntity makeAccountDepositValue(AccountEntity accountEntity, BigDecimal value){
        BigDecimal newAccountBalance = accountEntity.getAccountBalance().add(value);
        accountEntity.setAccountBalance(newAccountBalance);
        return accountRepository.save(accountEntity);
    }

    private AccountEntity makeAccountWithdrawValue(AccountEntity accountEntity, BigDecimal value){
        BigDecimal newAccountBalance = accountEntity.getAccountBalance().subtract(value);
        if(newAccountBalance.signum() < 0){
            throw new InvalidWithdrawException();
        }
        accountEntity.setAccountBalance(newAccountBalance);
        return accountRepository.save(accountEntity);
    }

    private void saveAccountCashFlowHistory(AccountEntity account, BigDecimal value){
        AccountValueFlowEntity accountValueFlowEntity = AccountValueFlowEntity.builder().account(account).value(value).build();
        accountValueFlowRepository.save(accountValueFlowEntity);
    }

    private void saveAccountTransferHistory(AccountEntity senderAccountEntity, AccountEntity beneficiaryAccountEntity, BigDecimal value, String message){
        AccountTransferEntity accountTransferEntity = AccountTransferEntity.builder().
                transactionTime(LocalDateTime.now()).message(message).sender(senderAccountEntity).
                beneficiary(beneficiaryAccountEntity).value(value.negate()).build();
        transferRepository.save(accountTransferEntity);

    }

    private AccountEntity getAccountEntity(String accountNumber){
        return accountRepository.findByAccountNumberEquals(accountNumber).orElseThrow(()-> new AccountNotFoundException(accountNumber));
    }

    private void checkTransactionValue(BigDecimal value){
        if (value.signum() <= 0){
            throw new InvalidValueException(value);
        }
    }

    private void checkTransactionAccountNotSame(String senderAccountNumber, String beneficiaryAccountNumber){
        if (Objects.equals(senderAccountNumber, beneficiaryAccountNumber)){
            throw new InvalidTransactionParameterException(senderAccountNumber, beneficiaryAccountNumber);
        }
    }

    public List<List<AccountHistory>> getUserHIstory(Account account){
        String accountNumber = account.getAccountNumber();
        AccountEntity accountEntity = getAccountEntity(accountNumber);
        List<AccountValueFlowEntity> accountValueFlowEntity = accountValueFlowRepository.findAllByAccountOrderByIdDesc(accountEntity);
        List<AccountTransferEntity> accountTransferEntity = transferRepository.findAccountTransfersList(accountEntity.getId());
        return new ArrayList<>(Arrays.asList(generateAccountCashFlowHist(accountValueFlowEntity), generateAccountTransferHist(accountTransferEntity, accountEntity)));
    }

    private List<AccountHistory> generateAccountCashFlowHist(List<AccountValueFlowEntity> accountValueFlowEntity){
        List<AccountHistory> accountCashFlows = new ArrayList<>();
        for (AccountValueFlowEntity acc: accountValueFlowEntity) {
            if (acc.getValue().signum() < 0){
                accountCashFlows.add(new AccountHistory(HistoryType.WITHDRAW,acc.getValue()));
            }else {
                accountCashFlows.add(new AccountHistory(HistoryType.DEPOSIT,acc.getValue()));
            }
        }
        return accountCashFlows;
    }

    private List<AccountHistory> generateAccountTransferHist(List<AccountTransferEntity> accountTransferEntity, AccountEntity accountEntity){
        ArrayList<AccountHistory> accountAccountHistoryTransfer = new ArrayList<>();
        for (AccountTransferEntity t: accountTransferEntity) {
            if (Objects.equals(t.getSender().getId(), accountEntity.getId())){
                accountAccountHistoryTransfer.add(new AccountHistory(HistoryType.SENDMONEY,t.getValue().negate()));
            }else {
                accountAccountHistoryTransfer.add(new AccountHistory(HistoryType.GETMONEY,t.getValue()));
            }
        }
        return accountAccountHistoryTransfer;
    }

}
