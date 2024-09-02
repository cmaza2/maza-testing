package com.maza.accountsmovementsservice.domain.entities;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class TransactionsReport {
    Date date;
    String customer;
    String accountNumber;
    String accountType;
    BigDecimal initialBalance;
    boolean status;
    String  transactionType;
    BigDecimal value;
    BigDecimal balance;


}
