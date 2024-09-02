package com.maza.accountsmovementsservice.aplication.services;


import com.maza.accountsmovementsservice.aplication.mapper.AccountDtoMapper;
import com.maza.accountsmovementsservice.aplication.mapper.AccountRequestMapper;
import com.maza.accountsmovementsservice.aplication.usecases.AccountsUseCase;
import com.maza.accountsmovementsservice.domain.dto.AccountDTO;
import com.maza.accountsmovementsservice.domain.dto.request.AccountRequestDTO;
import com.maza.accountsmovementsservice.domain.entities.Account;
import com.maza.accountsmovementsservice.domain.port.AccountPersistencePort;
import com.maza.accountsmovementsservice.domain.port.TransactionPersistencePort;
import com.maza.accountsmovementsservice.domain.repository.AccountRepository;
import com.maza.accountsmovementsservice.infraestructure.dto.CustomerDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServices implements AccountsUseCase {
    private final AccountPersistencePort accountPersistencePort;
    private final AccountRequestMapper accountRequestMapper;
    private final AccountDtoMapper accountDtoMapper;

    @Autowired
    public AccountServices(final AccountPersistencePort accountPersistencePort,
                           final AccountRequestMapper accountRequestMapper,
                           final AccountDtoMapper accountDtoMapper) {
        this.accountPersistencePort = accountPersistencePort;
        this.accountRequestMapper = accountRequestMapper;
        this.accountDtoMapper = accountDtoMapper;

    }

    /**
     * Method that calls the repository and list all accounts
     *
     * @return List of accounts.
     */
    @Override
    public List<AccountDTO> getAccounts() {
        var account = accountPersistencePort.findAll();
        return  account.stream()
                .map(accountDtoMapper::toDto)
                .collect(Collectors.toList());
    }
    /**
     * Method that calls the repository and recovery an account by identification
     *
     * @param identification Id Number.
     * @return Account information
     */
    @Override
    public List<AccountDTO> findByIdentification(String identification) {
        var account =  accountPersistencePort.findByIdentification(identification);
        return  account.stream()
                .map(accountDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Method that calls the repository and create an account
     *
     * @param accountRequestDTO Account.
     * @return Account information
     */
    @Override
    public AccountDTO create(AccountRequestDTO accountRequestDTO,CustomerDTO customerDTO) {
        var customer = accountRequestMapper.toDomain(accountRequestDTO);
        customer.setIdCustomer(customerDTO.getIdCustomer());
        var customerCreated = accountPersistencePort.save(customer);
        return accountDtoMapper.toDto(customerCreated);
    }

    @Override
    public AccountDTO update(Long id,Long idCustomer,AccountRequestDTO accountRequestDTO) {
        var account = accountRequestMapper.toDomain(accountRequestDTO);
        account.setIdAccount(id);
        account.setIdCustomer(idCustomer);
        var accountUpdate = accountPersistencePort.update(account);
        return accountDtoMapper.toDto(accountUpdate);
    }

    /**
     * Method that calls the repository and return an account by id
     *
     * @param accountId id of account.
     * @return Account information
     */
    @Override
    public AccountDTO getAccountById(Long accountId) {
        var account =  accountPersistencePort.findById(accountId);
        return accountDtoMapper.toDto(account);
    }
    /**
     * Method that calls the repository and deletes an account
     *
     * @param accountId id of account.
     */
    @Override
    public void deleteAccount(Long accountId) {
        accountPersistencePort.deleteById(accountId);
    }

    /**
     * Method that calls the repository and return an account
     *
     * @param accountNumber number of account.
     * @return Account information
     */
    @Override
    public AccountDTO getAccountInformation(String accountNumber) {
        var account = accountPersistencePort.getAccountInformation(accountNumber);
        return accountDtoMapper.toDto(account);
    }

}
