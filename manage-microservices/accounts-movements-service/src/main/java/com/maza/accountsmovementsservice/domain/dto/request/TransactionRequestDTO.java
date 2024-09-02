package com.maza.accountsmovementsservice.domain.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionRequestDTO {
    @NotBlank(message = "Account cannot be empty")
    @Size(min = 5, max = 20, message = "Account must be between 1 and 20 characters")
    private String account;

    private String idAccount;

    @NotNull(message = "Date cannot be null")
    @PastOrPresent(message = "Date must be in the past or present")
    private LocalDate date;

    @NotBlank(message = "Transaction type cannot be empty")
    @Pattern(regexp = "^(Deposito|Retiro)$", message = "Transaction type must be either 'Deposito' or 'Retiro'")
    private String transactionType;

    @NotNull(message = "Value cannot be null")
    private BigDecimal value;
}
