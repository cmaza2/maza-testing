package com.maza.accountsmovementsservice.infraestructure.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
/**
 * Account Entity.
 */
@Entity
@Getter
@Setter
@Table(name = "taccounts")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idAccount;

    private Long idCustomer;
    private String accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private boolean status;


}
