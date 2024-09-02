package com.maza.accountsmovementsservice.domain.dto.request;


import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequestDTO {
    @NotBlank(message = "Account number cannot be empty")
    @Size(min = 5, max = 20, message = "Account number must be between 5 and 20 characters")
    private String accountNumber;
    @NotBlank(message = "Account type cannot be empty")
    @Pattern(regexp = "^(Ahorros|Corriente)$", message = "Account type must be either 'SAVINGS' or 'CHECKING'")
    private String accountType;
    @NotNull(message = "Initial balance cannot be null")
    @DecimalMin(value = "0.0", message = "Initial balance must be zero or positive")
    private BigDecimal initialBalance;
    @AssertTrue(message = "Status must be true or false")
    private boolean status;
    @NotBlank(message = "Customer cannot be empty")
    @Size(min = 10, max = 10, message = "Customer identification must be 10 characters")
    private String customer;
}
