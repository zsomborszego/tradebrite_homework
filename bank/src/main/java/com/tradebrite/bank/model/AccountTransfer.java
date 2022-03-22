package com.tradebrite.bank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountTransfer {

    private LocalDateTime transactionTime;
    private String message;
    Account sender;
    Account beneficiary;
    private BigDecimal value;

}
