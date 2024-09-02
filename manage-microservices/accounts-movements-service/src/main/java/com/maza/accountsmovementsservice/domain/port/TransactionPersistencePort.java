package com.maza.accountsmovementsservice.domain.port;

import com.maza.accountsmovementsservice.domain.entities.Transaction;
import com.maza.accountsmovementsservice.domain.entities.Transactions;
import com.maza.accountsmovementsservice.infraestructure.dto.CustomerDTO;

import java.time.LocalDate;
import java.util.List;

public interface TransactionPersistencePort {
    Transaction save(Transaction transaction);
    Transaction findById(Long id);
    void deleteById(Long id);
    List<Transaction> findAll();
    Transaction update(Transaction transaction);

    Transaction findFirstByAccountNumberOrderByidDesc(Long idAccount);

    List<Transactions> getMovementsByUserAndDate(LocalDate initDate, LocalDate finalDate, CustomerDTO customer);
    List<Transactions> getMovementsByAccounts(LocalDate initDate, LocalDate finalDate, List<Long> accounts);
}
