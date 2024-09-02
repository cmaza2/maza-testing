package com.maza.accountsmovementsservice.infraestructure.adapter;

import com.maza.accountsmovementsservice.domain.entities.Account;
import com.maza.accountsmovementsservice.domain.port.AccountPersistencePort;
import com.maza.accountsmovementsservice.domain.repository.AccountRepository;
import com.maza.accountsmovementsservice.infraestructure.mapper.AccountDboMapper;
import com.maza.accountsmovementsservice.infraestructure.util.AccountException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountJpaAdapter implements AccountPersistencePort {
    private final AccountRepository accountRepository;
    private final AccountDboMapper accountDboMapper;
    private static final String MESSAGE_ACCOUNT="ACCOUNT NOT FOUND";

    public AccountJpaAdapter(AccountRepository accountRepository, AccountDboMapper accountDboMapper) {
        this.accountRepository = accountRepository;
        this.accountDboMapper = accountDboMapper;
    }
    @Override
    public Account save(Account accountDomain) {
        var userToSave = accountDboMapper.toDbo(accountDomain);
        var userSaved = accountRepository.save(userToSave);
        return accountDboMapper.toDomain(userSaved);
    }
    @Override
    public Account update(Account customerDomain) {
        var userToUpdate = accountDboMapper.toDbo(customerDomain);
        var userUpdated = accountRepository.save(userToUpdate);
        return accountDboMapper.toDomain(userUpdated);
    }

    @Override
    public List<Account> findByIdentification(String id) {
        var optionalAccount = accountRepository.findAccountByIdentification(id);
        var account=optionalAccount
                .stream()
                .map(accountDboMapper::toDomain)
                .collect(Collectors.toList());
        return account;
    }

    @Override
    public Account findById(Long id) {
        var optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isEmpty()){
           throw new AccountException(HttpStatus.NOT_FOUND,
                    String.format(MESSAGE_ACCOUNT, id));
        }

        return accountDboMapper.toDomain(optionalAccount.get());
    }


    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(accountDboMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Account getAccountInformation(String accountNumber) {
        var optionalAccount = accountRepository.findAccountByAccountNumber(accountNumber);
        if (optionalAccount.isEmpty()){
            throw new AccountException(HttpStatus.NOT_FOUND,
                    String.format(MESSAGE_ACCOUNT, accountNumber));
        }

        return accountDboMapper.toDomain(optionalAccount.get());
    }
}
