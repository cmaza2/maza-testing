package com.maza.accountsmovementsservice.domain.entities;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Transaction Entity.
 */
@Data
public class Transaction {

    private Long idTransaction;
    private Long idAccount;
    private String account;
    private LocalDate date;
    private String transactionType;
    private BigDecimal value;
    private BigDecimal balance;
}
