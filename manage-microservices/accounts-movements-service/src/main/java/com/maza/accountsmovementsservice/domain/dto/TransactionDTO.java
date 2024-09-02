package com.maza.accountsmovementsservice.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionDTO {
    private int idTransaction;
    private Long idAccount;
    private String account;
    private LocalDate date;
    private String transactionType;
    private BigDecimal value;
    private BigDecimal balance;
}
