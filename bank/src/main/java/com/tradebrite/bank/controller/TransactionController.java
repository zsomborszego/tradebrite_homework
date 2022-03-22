package com.tradebrite.bank.controller;

import com.tradebrite.bank.model.*;
import com.tradebrite.bank.service.exceptions.AccountNotFoundException;
import com.tradebrite.bank.service.TransactionServiceImpl;
import com.tradebrite.bank.service.exceptions.InvalidTransactionParameterException;
import com.tradebrite.bank.service.exceptions.InvalidValueException;
import com.tradebrite.bank.service.exceptions.InvalidWithdrawException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionServiceImpl transactionService;

    @PostMapping("/deposit")
    private ResponseEntity<String> depostiMoney(@RequestBody AccountTransaction accountTransaction) {
        return ResponseEntity.ok().body(transactionService.depositMoneyHandler(accountTransaction).getStatusMessage());
    }


    @PostMapping("/withdraw")
    private ResponseEntity<String> withdrawMoney(@RequestBody AccountTransaction accountTransaction) {
        return ResponseEntity.ok().body(transactionService.withdrawMoneyHandler(accountTransaction).getStatusMessage());
    }

    @PostMapping("/transfer")
    private ResponseEntity<String> transferData(@RequestBody AccountTransfer accountTransfer) {
        return ResponseEntity.ok().body(transactionService.transferMoneyHandler(accountTransfer).getStatusMessage());
    }

    @PostMapping("/history/account")
    private ResponseEntity<List<List<AccountHistory>>> getUserTransactionHIstory(@RequestBody Account account){
        return ResponseEntity.ok().body(transactionService.getUserHIstory(account));
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> accountNotFoundHandler(AccountNotFoundException accountNotFoundException) {
        return new ResponseEntity<String>(accountNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<String> invalidValueHandler(InvalidValueException invalidValueException) {
        return new ResponseEntity<String>(invalidValueException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidWithdrawException.class)
    public ResponseEntity<String> invalidWithdrawHandler(InvalidWithdrawException invalidWithdrawException) {
        return new ResponseEntity<String>(invalidWithdrawException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTransactionParameterException.class)
    public ResponseEntity<String> invalidTransactionHandler(InvalidTransactionParameterException invalidTransactionParameterException) {
        return new ResponseEntity<String>(invalidTransactionParameterException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> nullPointerHandler(NullPointerException nullPointerException) {
        return new ResponseEntity<String>(nullPointerException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
