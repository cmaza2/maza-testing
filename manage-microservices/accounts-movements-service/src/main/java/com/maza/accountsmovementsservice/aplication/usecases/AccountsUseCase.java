package com.maza.accountsmovementsservice.aplication.usecases;

import com.maza.accountsmovementsservice.domain.dto.AccountDTO;
import com.maza.accountsmovementsservice.domain.dto.request.AccountRequestDTO;
import com.maza.accountsmovementsservice.infraestructure.dto.CustomerDTO;

import java.util.List;

public interface AccountsUseCase {
    AccountDTO create(AccountRequestDTO accountRequestDTO, CustomerDTO customerDTO);
    AccountDTO update(Long id,Long idCustomer,AccountRequestDTO accountRequestDTO);
    AccountDTO getAccountById(Long accountId);
    void deleteAccount(Long accountId);
    List<AccountDTO> getAccounts();
    List<AccountDTO> findByIdentification(String id);
    AccountDTO getAccountInformation(String accountNumber);


}
