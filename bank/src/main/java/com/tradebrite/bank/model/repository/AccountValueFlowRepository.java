package com.tradebrite.bank.model.repository;

import com.tradebrite.bank.model.entity.AccountEntity;
import com.tradebrite.bank.model.entity.AccountValueFlowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountValueFlowRepository extends JpaRepository<AccountValueFlowEntity, Long> {

    List<AccountValueFlowEntity> findAllByAccountOrderByIdDesc(AccountEntity accountEntity);
}
