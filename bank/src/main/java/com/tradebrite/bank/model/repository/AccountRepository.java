package com.tradebrite.bank.model.repository;

import com.tradebrite.bank.model.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity>  findByAccountNumberEquals(String accountNUmber);

}
