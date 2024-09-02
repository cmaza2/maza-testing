package com.maza.accountsmovementsservice.domain.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class TransactionRequestDTO {

    private String account;
    private String idAccount;
    private Date date;
    private String transactionType;
    private BigDecimal value;
}
