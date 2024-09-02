package com.maza.accountsmovementsservice.infraestructure.util;


import com.maza.accountsmovementsservice.domain.entities.Account;

import java.math.BigDecimal;

public class TypeMovement {
    /**
     * Method that obtain a nullable properties
     *
     * @param tyoeMovement Type of transaction
     * @param balance      balance of account
     * @param value        value of transaction
     * @return List of empty values.
     */

    public static final BigDecimal movement(String tyoeMovement, BigDecimal balance, BigDecimal value) throws Exception {
        if (tyoeMovement.toLowerCase().equals("retiro")) {
            return withdrawal(balance, value);
        } else if (tyoeMovement.toLowerCase().equals("deposito")) {
            return deposit(balance, value);
        } else {
            throw new Exception("Type of movement invalid, Deposit or Withdrawal must be selected");
        }
    }

    /**
     * method that makes a deposit
     *
     * @param balance Balance of account
     * @param value   value of transaction
     * @return List of empty values.
     */
    public static BigDecimal deposit(BigDecimal balance, BigDecimal value) {
        return balance.add(value);
    }

    /**
     * method that makes a withdrawal
     *
     * @param balance Balance of account
     * @param value   value of transaction
     * @return List of empty values.
     */

    public static BigDecimal withdrawal(BigDecimal balance, BigDecimal value) {
        if (balance.compareTo(value) >= 0) {
            return balance.subtract(value);
        } else {
            throw new RuntimeException("Unavailable balance");
        }
    }

    /**
     * Method that validate that account exists
     *
     * @param account       Account Information
     * @param accountNumber Account number
     * @return List of empty values.
     */
    public static final Account validateExistAccount(Account account, String accountNumber){
        if (account == null) {
            throw new RuntimeException("Account " + accountNumber + " doesn't exist");
        }
        return account;
    }
}
