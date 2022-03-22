package com.tradebrite.bank.model.entity;

import com.tradebrite.bank.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(unique=true)
    private String email;
    @Column(unique=true)
    private String username;
    private String accountNumber;
    private BigDecimal accountBalance;
    private LocalDateTime registrationTime;


    public AccountEntity(Account account){
        this.name = account.getName();
        this.email = account.getEmail();
        this.username = account.getUsername();
        this.accountNumber = account.getAccountNumber();
        this.accountBalance = account.getAccountBalance();
        this.registrationTime = account.getRegistrationTime();
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountBalance=" + accountBalance +
                ", registrationTime=" + registrationTime +
                '}';
    }
}
