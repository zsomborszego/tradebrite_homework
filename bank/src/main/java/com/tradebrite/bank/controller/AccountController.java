package com.tradebrite.bank.controller;

import com.tradebrite.bank.model.Account;

import com.tradebrite.bank.model.entity.AccountEntity;
import com.tradebrite.bank.service.AccountServiceImpl;
import com.tradebrite.bank.service.exceptions.AccountAlreadyExistException;
import com.tradebrite.bank.service.exceptions.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@CrossOrigin
@RequiredArgsConstructor
@RestController()
@RequestMapping("/api/account")
public class AccountController {

    private final AccountServiceImpl accountServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<String> registerAccount(@RequestBody Account account){
        return ResponseEntity.ok().body(accountServiceImpl.registerAccount(account).getStatusMessage());
    }

    @PostMapping("/search")
    private ResponseEntity<AccountEntity> getAccountEntity(@RequestBody Account account){
        return ResponseEntity.ok().body(accountServiceImpl.findAccount(account));
    }

    @GetMapping("/list")
    public ResponseEntity<List<AccountEntity>> getAlluser(){
        return ResponseEntity.ok().body(accountServiceImpl.getAllAccount());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> accountNotFoundHandler(AccountNotFoundException accountNotFoundException) {
        return new ResponseEntity<String>(accountNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountAlreadyExistException.class)
    public ResponseEntity<String> accountExistHandler(AccountAlreadyExistException accountAlreadyExistException) {
        return new ResponseEntity<String>(accountAlreadyExistException.getMessage(), HttpStatus.CONFLICT);
    }
}
