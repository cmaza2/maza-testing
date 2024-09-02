package com.maza.accountsmovementsservice.aplication.services;


import com.maza.accountsmovementsservice.aplication.mapper.TransactionDtoMapper;
import com.maza.accountsmovementsservice.aplication.mapper.TransactionRequestMapper;
import com.maza.accountsmovementsservice.domain.dto.TransactionDTO;
import com.maza.accountsmovementsservice.domain.dto.request.TransactionRequestDTO;
import com.maza.accountsmovementsservice.domain.entities.Account;
import com.maza.accountsmovementsservice.domain.entities.Transactions;
import com.maza.accountsmovementsservice.domain.port.AccountPersistencePort;
import com.maza.accountsmovementsservice.domain.port.TransactionPersistencePort;
import com.maza.accountsmovementsservice.infraestructure.dto.CustomerDTO;
import com.maza.accountsmovementsservice.infraestructure.dto.TransactionsDTO;
import com.maza.accountsmovementsservice.aplication.usecases.TransactionUseCase;
import com.maza.accountsmovementsservice.infraestructure.util.TypeMovement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServices implements TransactionUseCase {
    private TransactionPersistencePort transactionPersistencePort;
    private AccountPersistencePort accountPersistencePort;
    private TransactionRequestMapper transactionRequestMapper;
    private TransactionDtoMapper transactionDtoMapper;

    @Autowired
    public TransactionServices(AccountPersistencePort accountPersistencePort,
                               TransactionPersistencePort transactionPersistencePort,
                               TransactionRequestMapper transactionRequestMapper,
                               TransactionDtoMapper transactionDtoMapper
                               ) {
        this.transactionPersistencePort = transactionPersistencePort;
        this.transactionRequestMapper = transactionRequestMapper;
        this.transactionDtoMapper = transactionDtoMapper;
        this.accountPersistencePort = accountPersistencePort;
    }

    /**
     * Method that calls the repository and list all transactions
     *
     * @return List of transactions.
     */
    @Override
    public List<TransactionDTO> getTransactions() {
        var transaction = transactionPersistencePort.findAll();
        return transaction.stream()
                .map(transactionDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Method that calls the repository and return an transaction by id
     *
     * @param transactionId id of transaction.
     * @return Transaction information
     */
    @Override
    public TransactionDTO getTransactionById(Long transactionId) {
        var transaction = transactionPersistencePort.findById(transactionId);
        return transactionDtoMapper.toDto(transaction);
    }

    /**
     * Method that calls the repository and create an transaction
     *
     * @param transactionRequestDTO Tranction generated.
     * @return Transaction information
     */
    @Override
    public TransactionDTO createTransaction(TransactionRequestDTO transactionRequestDTO) throws Exception {
        String accountNumber = transactionRequestDTO.getAccount();
        Account account = accountPersistencePort.getAccountInformation(accountNumber);
        account = TypeMovement.validateExistAccount(account, accountNumber);
        BigDecimal balance = getBalance(account.getAccountNumber());
        balance = balance == null ? account.getInitialBalance() : balance;
        balance = TypeMovement.movement(transactionRequestDTO.getTransactionType(), balance, transactionRequestDTO.getValue());
        var transaction = transactionRequestMapper.toDomain(transactionRequestDTO);
        transaction.setIdAccount(account.getIdAccount());
        transaction.setBalance(balance);
        transaction.setDate(LocalDate.now());
        transaction.setValue(transaction.getTransactionType().toLowerCase()
                .equals("retiro")?new BigDecimal("-"+transaction.getValue()):transaction.getValue());
        var transactionCreated = transactionPersistencePort.save(transaction);
        return transactionDtoMapper.toDto(transactionCreated);
    }

    /**
     * Method that calls the repository and create an transaction
     *
     * @param id                    Tranction generated.
     * @param transactionRequestDTO Tranction generated.
     * @return Transaction information
     */
    @Override
    public TransactionDTO updateTransaction(Long id, TransactionRequestDTO transactionRequestDTO) {
        Account account = accountPersistencePort.getAccountInformation(transactionRequestDTO.getAccount());
        var transaction = transactionRequestMapper.toDomain(transactionRequestDTO);
        transaction.setIdTransaction(id);
        transaction.setIdAccount(account.getIdAccount());
        var transactionCreated = transactionPersistencePort.save(transaction);
        return transactionDtoMapper.toDto(transactionCreated);
    }

    /**
     * Method that calls the repository and delete a transaction
     *
     * @param transactionId id of transaction.
     */
    @Override
    public void deleteTransaction(Long transactionId) {
        transactionPersistencePort.deleteById(transactionId);

    }

    /**
     * Method that calls the repository and get the actual balance of a transaction
     *
     * @param accountNumber id of transaction.
     * @return Balance available
     */
    @Override
    public BigDecimal getBalance(String accountNumber) {
        var transaction = transactionPersistencePort.findFirstByAccountNumberOrderByidDesc( accountPersistencePort.getAccountInformation(accountNumber).getIdAccount());
        BigDecimal balance = transaction != null ? transaction.getBalance() : null;
        return balance;
    }

    /**
     * Method that calls the repository and get the transactions by customers and dates
     *
     * @param initDate   initial Date
     * @param finalDate  final Date
     * @param customer Customer Id
     * @param customer   Name customer Date
     * @return List of transactions
     */
    @Override
    public List<TransactionsDTO> getMovementsByUserAndDate(LocalDate initDate, LocalDate finalDate, CustomerDTO customer) {
        var accounts=accountPersistencePort.findByIdentification(customer.getIdCustomer().toString());
        var accountsId = accounts.stream()
                .map(Account::getIdAccount)
                .collect(Collectors.toList());
        List<Transactions> transaction = transactionPersistencePort.getMovementsByAccounts(initDate,finalDate,accountsId);

        List<TransactionsDTO> lstTransaction = new ArrayList<>();
        for (Transactions trx : transaction) {
            Account accountsClient=accountPersistencePort.findById(trx.getIdAccount());
            TransactionsDTO transactionsDTO = new TransactionsDTO();
            transactionsDTO.setDate(Date.valueOf(trx.getDate()));
            transactionsDTO.setCustomer(customer.getName());
            transactionsDTO.setAccountNumber(accountsClient.getAccountNumber());
            transactionsDTO.setAccountType(accountsClient.getAccountType());
            transactionsDTO.setInitialBalance(accountsClient.getInitialBalance());
            transactionsDTO.setStatus(accountsClient.isStatus());
            transactionsDTO.setTransactionType(trx.getTransactionType());
            transactionsDTO.setValue(trx.getValue());
            transactionsDTO.setBalance(trx.getBalance());
            lstTransaction.add(transactionsDTO);
        }
        return lstTransaction;
    }
}
