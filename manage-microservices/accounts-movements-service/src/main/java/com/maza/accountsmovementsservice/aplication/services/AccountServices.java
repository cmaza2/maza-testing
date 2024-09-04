package com.maza.accountsmovementsservice.aplication.services;


import com.maza.accountsmovementsservice.aplication.mapper.AccountDtoMapper;
import com.maza.accountsmovementsservice.aplication.mapper.AccountRequestMapper;
import com.maza.accountsmovementsservice.aplication.usecases.AccountsUseCase;
import com.maza.accountsmovementsservice.domain.dto.Account;
import com.maza.accountsmovementsservice.domain.dto.request.AccountRequestDTO;
import com.maza.accountsmovementsservice.domain.port.AccountPersistencePort;
import com.maza.accountsmovementsservice.domain.dto.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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
    public List<Account> getAccounts() {
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
    public List<Account> findByIdentification(String identification) {
        log.info("Cuenta a buscar por identificacion: ",identification);
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
    public Account create(AccountRequestDTO accountRequestDTO, CustomerDTO customerDTO) {
        var customer = accountRequestMapper.toDomain(accountRequestDTO);
        customer.setIdCustomer(customerDTO.getIdCustomer());
        var customerCreated = accountPersistencePort.save(customer);
        var customerResponseDTO = accountDtoMapper.toDto(customerCreated);
        log.info("Trama de salida crear cuenta: {}",customerResponseDTO);
        return customerResponseDTO;
    }

    @Override
    public Account update(Long id, Long idCustomer, AccountRequestDTO accountRequestDTO) {
        var account = accountRequestMapper.toDomain(accountRequestDTO);
        account.setIdAccount(id);
        account.setIdCustomer(idCustomer);
        var accountUpdate = accountPersistencePort.update(account);
        var accountResponseDto = accountDtoMapper.toDto(accountUpdate);
        log.info("Trama de salida actualizar cuenta: {}",accountResponseDto);
        return accountResponseDto;
    }

    /**
     * Method that calls the repository and return an account by id
     *
     * @param accountId id of account.
     * @return Account information
     */
    @Override
    public Account getAccountById(Long accountId) {
        var account =  accountPersistencePort.findById(accountId);
        var accountDTO = accountDtoMapper.toDto(account);
        log.info("Id y trama de salida obtener cuenta: id={},trama={}",accountId,accountDTO);
        return accountDTO;
    }
    /**
     * Method that calls the repository and deletes an account
     *
     * @param accountId id of account.
     */
    @Override
    public void deleteAccount(Long accountId) {
        accountPersistencePort.deleteById(accountId);
        log.info("Cuenta con id {} eliminada correctamente",accountId);
    }

    /**
     * Method that calls the repository and return an account
     *
     * @param accountNumber number of account.
     * @return Account information
     */
    @Override
    public Account getAccountInformation(String accountNumber) {
        var account = accountPersistencePort.getAccountInformation(accountNumber);
        var accountRequestDTO=accountDtoMapper.toDto(account);
        log.info("Información de la cuenta {} :{}",accountNumber,accountRequestDTO);
        return accountRequestDTO;
    }

}
