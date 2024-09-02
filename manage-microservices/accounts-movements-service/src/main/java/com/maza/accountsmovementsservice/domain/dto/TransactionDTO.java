package com.maza.accountsmovementsservice.domain.dto;

import com.maza.accountsmovementsservice.domain.entities.Account;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class TransactionDTO {
    private int idTransaction;
    private Long idAccount;
    private String account;
    private Date date;
    private String transactionType;
    private BigDecimal value;
    private BigDecimal balance;
}
