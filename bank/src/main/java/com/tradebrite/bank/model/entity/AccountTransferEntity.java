package com.tradebrite.bank.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "transfer")
@Data
public class AccountTransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime transactionTime;
    private String message;
    @ManyToOne
    AccountEntity sender;
    @ManyToOne
    AccountEntity beneficiary;
    private BigDecimal value;


}
