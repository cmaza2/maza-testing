package com.maza.accountsmovementsservice.domain.dto.request;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequestDTO {
    private String accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private boolean status;
    private String customer;
}
