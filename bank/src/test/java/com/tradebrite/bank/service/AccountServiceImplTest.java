package com.tradebrite.bank.service;

import com.tradebrite.bank.model.Account;
import com.tradebrite.bank.model.ResponseMessages;
import com.tradebrite.bank.model.entity.AccountEntity;
import com.tradebrite.bank.model.repository.AccountRepository;
import com.tradebrite.bank.service.exceptions.AccountAlreadyExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static com.tradebrite.bank.model.Account.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    private AccountRepository accountRepository;

    private Random rand;

    private Account account;

    private AccountEntity accountEntity;

    private AccountServiceImpl accountServiceImpl;

    @BeforeEach
    void setUp() {
        rand = new Random();
        accountRepository = mock(AccountRepository.class);
        accountServiceImpl = new AccountServiceImpl(accountRepository, rand);
        account = builder().email("john@gmail.com").name("John").username("John").build();
        accountEntity = new AccountEntity(account);
        accountEntity.setAccountNumber("12345678-12345678");
        accountEntity.setAccountBalance(BigDecimal.valueOf(1000000));
        accountEntity.setRegistrationTime(LocalDateTime.now());
    }

    @Test
    void testRegisterNewAccount() {
        ResponseMessages responseMessages = accountServiceImpl.registerAccount(account);
        assertEquals(responseMessages, ResponseMessages.SUCCESSFULSAVEACCOUNT);
    }

    @Test
    void testregisterNewAccountException() {
        when(accountRepository.save(any(AccountEntity.class))).thenThrow(RuntimeException.class);
        AccountAlreadyExistException thrown = Assertions.assertThrows(AccountAlreadyExistException.class, () -> {
            accountServiceImpl.registerAccount(account);
        });
    }


    @Test
    void testGetAllAccount() {
        List<AccountEntity> accountEntities = new ArrayList<>(Arrays.asList(accountEntity,accountEntity,accountEntity));
        when(accountRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(accountEntities);
        List<AccountEntity> result = accountServiceImpl.getAllAccount();
        assertEquals(result, accountEntities);
    }

    @Test
    void testfindAccount() {
        account.setAccountNumber("12345678-12345678");
        when(accountRepository.findByAccountNumberEquals("12345678-12345678")).thenReturn(Optional.of(accountEntity));
        AccountEntity result = accountServiceImpl.findAccount(account);
        assertEquals(result, accountEntity);
    }

    @Test
    void testfindAccountException() {
        account.setAccountNumber("12345678-12345678");
        when(accountRepository.findByAccountNumberEquals("12345678-12345678")).thenThrow(AccountAlreadyExistException.class);
        AccountAlreadyExistException thrown = Assertions.assertThrows(AccountAlreadyExistException.class, () -> {
            AccountEntity result = accountServiceImpl.findAccount(account);
        });
    }
}
