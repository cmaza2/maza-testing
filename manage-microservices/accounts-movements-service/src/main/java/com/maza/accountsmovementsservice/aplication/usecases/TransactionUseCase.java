package com.maza.accountsmovementsservice.aplication.usecases;

import com.maza.accountsmovementsservice.domain.dto.AccountDTO;
import com.maza.accountsmovementsservice.domain.dto.TransactionDTO;
import com.maza.accountsmovementsservice.domain.dto.request.TransactionRequestDTO;
import com.maza.accountsmovementsservice.domain.entities.Account;
import com.maza.accountsmovementsservice.infraestructure.dto.CustomerDTO;
import com.maza.accountsmovementsservice.infraestructure.dto.TransactionsDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionUseCase {
    TransactionDTO createTransaction(TransactionRequestDTO transactionRequestDTO) throws Exception;
    TransactionDTO updateTransaction(Long id,TransactionRequestDTO transactionRequestDTO);
    TransactionDTO getTransactionById(Long transactionId);
    List<TransactionDTO> getTransactions();
    void deleteTransaction(Long transactionId);
    BigDecimal getBalance(String accountNumber);
    List<TransactionsDTO> getMovementsByUserAndDate(LocalDate initDate, LocalDate finalDate,  CustomerDTO customer);

}
