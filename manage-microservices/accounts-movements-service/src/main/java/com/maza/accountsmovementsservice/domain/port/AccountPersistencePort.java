package com.maza.accountsmovementsservice.domain.port;

import com.maza.accountsmovementsservice.domain.entities.Account;

import java.util.List;

public interface AccountPersistencePort {
    Account save(Account account);
    Account findById(Long id);
    void deleteById(Long id);
    List<Account> findAll();
    Account update(Account account);
    List<Account> findByIdentification(String id);
    Account getAccountInformation(String accountNumber);
}
