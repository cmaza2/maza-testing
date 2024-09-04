package com.maza.accountsmovementsservice.aplication.usecases;

import com.maza.accountsmovementsservice.domain.dto.Account;
import com.maza.accountsmovementsservice.domain.dto.request.AccountRequestDTO;
import com.maza.accountsmovementsservice.domain.dto.CustomerDTO;

import java.util.List;

public interface AccountsUseCase {
    Account create(AccountRequestDTO accountRequestDTO, CustomerDTO customerDTO);
    Account update(Long id, Long idCustomer, AccountRequestDTO accountRequestDTO);
    Account getAccountById(Long accountId);
    void deleteAccount(Long accountId);
    List<Account> getAccounts();
    List<Account> findByIdentification(String id);
    Account getAccountInformation(String accountNumber);


}
