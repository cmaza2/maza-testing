package com.maza.accountsmovementsservice.infraestructure.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Transaction Entity.
 */
@Entity
@Getter
@Setter
@Table(name = "ttransactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaction;
    private Long idAccount;
    private LocalDate date;
    private String transactionType;
    private BigDecimal value;
    private BigDecimal balance;
}
