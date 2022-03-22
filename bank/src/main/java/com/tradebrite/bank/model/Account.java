package com.tradebrite.bank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {
    private String name;
    private String email;
    private String username;
    private String accountNumber;
    private BigDecimal accountBalance;
    private LocalDateTime registrationTime;
}

