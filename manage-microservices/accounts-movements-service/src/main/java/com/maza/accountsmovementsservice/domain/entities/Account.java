package com.maza.accountsmovementsservice.domain.entities;

import lombok.Data;

import java.math.BigDecimal;
/**
 * Account Entity.
 */
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
