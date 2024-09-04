package com.maza.accountsmovementsservice.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {
    private Long idAccount;

    private Long idCustomer;
    private String accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private boolean status;
    private String customer;
}
