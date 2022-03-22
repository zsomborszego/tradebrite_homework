package com.tradebrite.bank.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Account_value_flow")
public class AccountValueFlowEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    BigDecimal value;
    @ManyToOne
    AccountEntity account;

}
