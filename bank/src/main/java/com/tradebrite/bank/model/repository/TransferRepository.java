package com.tradebrite.bank.model.repository;

import com.tradebrite.bank.model.entity.AccountTransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransferRepository extends JpaRepository<AccountTransferEntity, Long> {


    @Query("SELECT t FROM AccountTransferEntity t WHERE t.beneficiary.id = ?1 OR t.sender.id = ?1 ORDER BY t.id DESC")
    List<AccountTransferEntity> findAccountTransfersList(Long id);

}
