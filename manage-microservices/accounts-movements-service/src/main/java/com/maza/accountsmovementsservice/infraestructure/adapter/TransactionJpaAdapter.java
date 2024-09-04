package com.maza.accountsmovementsservice.infraestructure.adapter;

import com.maza.accountsmovementsservice.domain.entities.Transaction;
import com.maza.accountsmovementsservice.domain.entities.Transactions;
import com.maza.accountsmovementsservice.domain.port.TransactionPersistencePort;
import com.maza.accountsmovementsservice.domain.repository.TransactionRepository;
import com.maza.accountsmovementsservice.domain.dto.CustomerDTO;
import com.maza.accountsmovementsservice.infraestructure.mapper.TransactionDboMapper;
import com.maza.accountsmovementsservice.infraestructure.util.TransactionException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionJpaAdapter implements TransactionPersistencePort {
    private final TransactionRepository transactionRepository;
    private final TransactionDboMapper transactionDboMapper;
    private static final String MESSAGE_TRANSACTION = "TRANSACTION NOT FOUND";

    @Autowired
    public TransactionJpaAdapter(TransactionRepository transactionRepository, TransactionDboMapper transactionDboMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionDboMapper = transactionDboMapper;
    }
    @Override
    public Transaction save(Transaction customerDomain) {
        var transactionToSave = transactionDboMapper.toDbo(customerDomain);
        var transactionSaved = transactionRepository.save(transactionToSave);
        return transactionDboMapper.toDomain(transactionSaved);

    }

    @Override
    public Transaction update(Transaction transaction) {
        var transactionToSave = transactionDboMapper.toDbo(transaction);
        var transactionSaved = transactionRepository.save(transactionToSave);
        return transactionDboMapper.toDomain(transactionSaved);
    }

    @Override
    public Transaction findById(Long id) {
        var optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isEmpty()) {
            throw new TransactionException(HttpStatus.NOT_FOUND,
                    String.format(MESSAGE_TRANSACTION, id));
        }

        return transactionDboMapper.toDomain(optionalTransaction.get());
    }


    @Override
    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll()
                .stream()
                .map(transactionDboMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Transaction findFirstByAccountNumberOrderByidDesc(Long idAccount) {

        var optionalTransaction = transactionRepository.getBalance(idAccount);
        if (optionalTransaction.isEmpty()) {
            return null;
        }
        return transactionDboMapper.toDomain(optionalTransaction.get());
    }

    @Override
    public List<Transactions> getMovementsByUserAndDate(LocalDate initDate, LocalDate finalDate, CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public List<Transactions> getMovementsByAccounts(LocalDate initDate, LocalDate finalDate, List<Long> accounts) {
        var movementsReport = transactionRepository.findMovementsByUserAndDate(initDate, finalDate, accounts);
        return movementsReport
                .stream()
                .map(transactionDboMapper::toDomainTransactions)
                .collect(Collectors.toList());
    }
}
