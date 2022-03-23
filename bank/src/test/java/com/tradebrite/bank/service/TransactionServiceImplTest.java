package com.tradebrite.bank.service;

import com.tradebrite.bank.model.*;
import com.tradebrite.bank.model.entity.AccountEntity;
import com.tradebrite.bank.model.entity.AccountTransferEntity;
import com.tradebrite.bank.model.entity.AccountValueFlowEntity;
import com.tradebrite.bank.model.repository.AccountRepository;
import com.tradebrite.bank.model.repository.AccountValueFlowRepository;
import com.tradebrite.bank.model.repository.HistoryType;
import com.tradebrite.bank.model.repository.TransferRepository;
import com.tradebrite.bank.service.exceptions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static com.tradebrite.bank.model.Account.builder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TransactionServiceImplTest {
    private AccountRepository accountRepository;

    private TransferRepository transferRepository;

    private AccountValueFlowRepository accountValueFlowRepository;

    private Account account;

    private Account beneficiaryAccount;

    private AccountEntity accountEntity;

    private AccountEntity beneficiaryAccountEntity;

    private AccountTransfer accountTransfer;

    private TransactionServiceImpl transactionServiceImpl;

    private AccountTransaction accountTransaction;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        transferRepository = mock(TransferRepository.class);
        accountValueFlowRepository = mock(AccountValueFlowRepository.class);

        transactionServiceImpl = new TransactionServiceImpl(accountRepository,transferRepository,accountValueFlowRepository);

        account = builder().email("john@gmail.com").name("John").username("John")
                .accountNumber("12345678-12345678").accountBalance(BigDecimal.valueOf(1000000))
                .registrationTime(LocalDateTime.now()).build();

        accountEntity = new AccountEntity(account);

        beneficiaryAccount = builder().email("Joe@gmail.com").name("Joe").username("Joe")
                .accountNumber("87654321-87654321").accountBalance(BigDecimal.valueOf(800000))
                .registrationTime(LocalDateTime.now()).build();

        beneficiaryAccountEntity = new AccountEntity(beneficiaryAccount);

        accountTransaction = new AccountTransaction(account,BigDecimal.valueOf(10));

        accountTransfer = AccountTransfer.builder().sender(account).beneficiary(beneficiaryAccount).build();
    }


    @Test
    void testDepositMoneyHandlerSuccess() {
        when(accountRepository.findByAccountNumberEquals("12345678-12345678")).thenReturn(Optional.of(accountEntity));
        ResponseMessages responseMessages = transactionServiceImpl.depositMoneyHandler(accountTransaction);
        assertEquals(responseMessages, ResponseMessages.SUCCESSFULDEPOSIT);
    }

    @Test
    void testDepositMoneyHandlerForInvalidValueException() {
        when(accountRepository.findByAccountNumberEquals("12345678-12345678")).thenReturn(Optional.of(accountEntity));
        accountTransaction.setValue(BigDecimal.valueOf(-8888));
        InvalidValueException thrown = Assertions.assertThrows(InvalidValueException.class, () -> {
            transactionServiceImpl.depositMoneyHandler(accountTransaction);
        });
    }


    @Test
    void testDepositMoneyHandlerForAccountNotFoundException() {
        when(accountRepository.findByAccountNumberEquals("12345678-12345678")).thenThrow(AccountNotFoundException.class);
        AccountNotFoundException thrown = Assertions.assertThrows(AccountNotFoundException.class, () -> {
            transactionServiceImpl.depositMoneyHandler(accountTransaction);
        });
    }


    @Test
    void testWithdrawMoneyHandlerSuccess() {
        when(accountRepository.findByAccountNumberEquals("12345678-12345678")).thenReturn(Optional.of(accountEntity));
        ResponseMessages responseMessages = transactionServiceImpl.withdrawMoneyHandler(accountTransaction);
        assertEquals(responseMessages, ResponseMessages.SUCCESSFULWITHDRAW);
    }

    @Test
    void testWithdrawMoneyHandlerForInvalidValueException() {
        accountTransaction.setValue(BigDecimal.valueOf(-8888));
        when(accountRepository.findByAccountNumberEquals("12345678-12345678")).thenReturn(Optional.of(accountEntity));
        InvalidValueException thrown = Assertions.assertThrows(InvalidValueException.class, () -> {
            transactionServiceImpl.withdrawMoneyHandler(accountTransaction);
        });
    }

    @Test
    void testWithdrawMoneyHandlerForInvalidWithdrawException() {
        accountTransaction.setValue(BigDecimal.valueOf(9999999));
        when(accountRepository.findByAccountNumberEquals("12345678-12345678")).thenReturn(Optional.of(accountEntity));
        InvalidWithdrawException thrown = Assertions.assertThrows(InvalidWithdrawException.class, () -> {
            transactionServiceImpl.withdrawMoneyHandler(accountTransaction);
        });
    }


    @Test
    void testWithdrawMoneyHandlerForAccountNotFoundException() {
        when(accountRepository.findByAccountNumberEquals("12345678-12345678")).thenThrow(AccountNotFoundException.class);
        AccountNotFoundException thrown = Assertions.assertThrows(AccountNotFoundException.class, () -> {
            transactionServiceImpl.depositMoneyHandler(accountTransaction);
        });
    }

    @Test
    void testTransferMoneyHandlerSuccess(){
        BigDecimal transferValue = BigDecimal.valueOf(80);
        accountTransfer.setValue(transferValue);
        when(accountRepository.findByAccountNumberEquals("87654321-87654321")).thenReturn(Optional.of(beneficiaryAccountEntity));
        when(accountRepository.findByAccountNumberEquals("12345678-12345678")).thenReturn(Optional.of(accountEntity));
        ResponseMessages responseMessages = transactionServiceImpl.transferMoneyHandler(accountTransfer);
        assertEquals(responseMessages, ResponseMessages.SUCCESSFULTRANSFER);

    }

    @Test
    void testTransferMoneyHandlerInvalidValueException(){
        BigDecimal transferValue = BigDecimal.valueOf(-888);
        accountTransfer.setValue(transferValue);
        when(accountRepository.findByAccountNumberEquals("87654321-87654321")).thenReturn(Optional.of(beneficiaryAccountEntity));
        when(accountRepository.findByAccountNumberEquals("12345678-12345678")).thenReturn(Optional.of(accountEntity));
        InvalidValueException thrown = Assertions.assertThrows(InvalidValueException.class, () -> {
            transactionServiceImpl.transferMoneyHandler(accountTransfer);
        });

    }

    @Test
    void testTransferMoneyHandlerInvalidWithdrawException(){
        BigDecimal transferValue = BigDecimal.valueOf(9999999);
        accountTransfer.setValue(transferValue);
        when(accountRepository.findByAccountNumberEquals("87654321-87654321")).thenReturn(Optional.of(beneficiaryAccountEntity));
        when(accountRepository.findByAccountNumberEquals("12345678-12345678")).thenReturn(Optional.of(accountEntity));
        InvalidWithdrawException thrown = Assertions.assertThrows(InvalidWithdrawException.class, () -> {
            transactionServiceImpl.transferMoneyHandler(accountTransfer);
        });

    }


    @Test
    void testTransferMoneyInvalidTransactionParameterException(){
        BigDecimal transferValue = BigDecimal.valueOf(10);
        accountTransfer.setValue(transferValue);
        accountTransfer.setSender(account);
        accountTransfer.setBeneficiary(account);
        when(accountRepository.findByAccountNumberEquals("12345678-12345678")).thenReturn(Optional.of(accountEntity));
        InvalidTransactionParameterException thrown = Assertions.assertThrows(InvalidTransactionParameterException.class, () -> {
            transactionServiceImpl.transferMoneyHandler(accountTransfer);
        });

    }

    @Test
    void testGetUserHistorySuccess(){
        accountEntity.setId(1L);
        AccountValueFlowEntity accountDeposit = AccountValueFlowEntity.builder().value(BigDecimal.valueOf(10)).account(accountEntity).build();
        AccountValueFlowEntity accountWithdraw = AccountValueFlowEntity.builder().value(BigDecimal.valueOf(-10)).account(accountEntity).build();
        AccountTransferEntity accounSendMoney = AccountTransferEntity.builder().sender(accountEntity).beneficiary(beneficiaryAccountEntity).value(BigDecimal.valueOf(10)).build();
        AccountTransferEntity accountGetMoney = AccountTransferEntity.builder().sender(beneficiaryAccountEntity).beneficiary(accountEntity).value(BigDecimal.valueOf(10)).build();

        List<AccountValueFlowEntity> accountValueFlowEntities = new ArrayList<AccountValueFlowEntity>(Arrays.asList(accountDeposit,accountWithdraw));
        List<AccountTransferEntity> accountTransferEntities = new ArrayList<AccountTransferEntity>(Arrays.asList(accounSendMoney,accountGetMoney));

        when(accountValueFlowRepository.findAllByAccountOrderByIdDesc(any(AccountEntity.class))).thenReturn(accountValueFlowEntities);
        when(transferRepository.findAccountTransfersList(any(Long.class))).thenReturn(accountTransferEntities);
        when(accountRepository.findByAccountNumberEquals("12345678-12345678")).thenReturn(Optional.of(accountEntity));
        List<List<AccountHistory>> result = transactionServiceImpl.getUserHIstory(account);

        AccountHistory accountHistoryDeposit = new AccountHistory(HistoryType.DEPOSIT, BigDecimal.valueOf(10));
        AccountHistory accountHistoryWithdraw = new AccountHistory(HistoryType.WITHDRAW, BigDecimal.valueOf(-10));
        AccountHistory accountHistorySendMoney = new AccountHistory(HistoryType.SENDMONEY, BigDecimal.valueOf(-10));
        AccountHistory accountHistoryGetMoney = new AccountHistory(HistoryType.GETMONEY, BigDecimal.valueOf(10));
        List<AccountHistory> cashFlowHiostroy = new ArrayList<AccountHistory>(Arrays.asList(accountHistoryDeposit,accountHistoryWithdraw));
        List<AccountHistory> transferHistoy = new ArrayList<AccountHistory>(Arrays.asList(accountHistorySendMoney,accountHistoryGetMoney));
        List<List<AccountHistory>> userAccountHistories =  new ArrayList<>(Arrays.asList(cashFlowHiostroy,transferHistoy));

        assertEquals(result, userAccountHistories);

    }


    @Test
    void testGetUserHistoryAccountNotFoundException(){
        when(accountRepository.findByAccountNumberEquals("12348678-12345678")).thenReturn(Optional.of(accountEntity));
        AccountNotFoundException thrown = Assertions.assertThrows(AccountNotFoundException.class, () -> {
            transactionServiceImpl.getUserHIstory(account);
        });

    }




}
