package com.maza.accountsmovementsservice.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
public class TransactionsDTO {
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
