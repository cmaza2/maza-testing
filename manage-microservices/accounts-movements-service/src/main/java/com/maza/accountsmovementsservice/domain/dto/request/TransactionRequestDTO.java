package com.maza.accountsmovementsservice.domain.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequestDTO {
    @NotBlank(message = "Account cannot be empty")
    @Size(min = 5, max = 20, message = "Account must be between 1 and 20 characters")
    private String account;
    private String idAccount;
    @NotBlank(message = "Transaction type cannot be empty")
    @Pattern(regexp = "^(Deposito|Retiro)$", message = "Transaction type must be either 'Deposito' or 'Retiro'")
    private String transactionType;

    @NotNull(message = "Value cannot be null")
    private BigDecimal value;
}
