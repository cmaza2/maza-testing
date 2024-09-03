package com.maza.accountsmovementsservice.infraestructure.adapter.in.unit;

import com.maza.accountsmovementsservice.domain.repository.AccountRepository;
import com.maza.accountsmovementsservice.infraestructure.entities.AccountEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Test
    void saveOrUpdate() {
        List<AccountEntity> lstCustomer=getCustomers();

        when(accountRepository.findAll()).thenReturn(lstCustomer);

        // Llamar al m?todo del servicio
        List<AccountEntity> customers = accountRepository.findAll();

        // Verificar el resultado
        assertEquals(2, customers.size());

        verify(accountRepository, times(1)).findAll();
    }
    private List<AccountEntity> getCustomers(){
        List<AccountEntity> lstCustomer = new ArrayList<>();
        for(int i=0;i<2;i++) {
            AccountEntity customerA = new AccountEntity();

            lstCustomer.add(customerA);
        }
        return lstCustomer;
    }
}
