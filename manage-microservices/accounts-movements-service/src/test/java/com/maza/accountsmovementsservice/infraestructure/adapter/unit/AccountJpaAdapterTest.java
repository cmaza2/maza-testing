package com.maza.accountsmovementsservice.infraestructure.adapter.unit;



import static org.junit.jupiter.api.Assertions.*;

import com.maza.accountsmovementsservice.domain.entities.Account;
import com.maza.accountsmovementsservice.domain.repository.AccountRepository;
import com.maza.accountsmovementsservice.infraestructure.adapter.AccountJpaAdapter;
import com.maza.accountsmovementsservice.infraestructure.entities.AccountEntity;
import com.maza.accountsmovementsservice.infraestructure.mapper.AccountDboMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AccountJpaAdapterTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountDboMapper accountDboMapper;
    @InjectMocks
    private AccountJpaAdapter accountJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void findAccountByCustomer() {
        List<AccountEntity> lstCustomer=getAccount();
        List<Account> account =getAccountEntity();
        when(accountRepository.findAccountByIdentification("1103040506")).thenReturn(lstCustomer);
        for (int i=0;i<lstCustomer.size();i++) {
            when(accountDboMapper.toDomain(lstCustomer.get(i))).thenReturn(account.get(i));
        }
        // Llamar al metodo del servicio
        List<Account> accounts = accountJpaAdapter.findByIdentification("1103040506");
        // Verificar el resultado
        assertNotNull(accounts);
        assertEquals("2901020304", accounts.get(0).getAccountNumber());
    }

    private List<AccountEntity> getAccount(){
        List<AccountEntity> lstAccountDTO = new ArrayList<>();
        AccountEntity accountDTO =new AccountEntity();
        accountDTO.setIdAccount(Long.valueOf("1"));
        accountDTO.setAccountType("Ahorros");
        accountDTO.setAccountNumber("2901020304");
        accountDTO.setStatus(true);
        accountDTO.setInitialBalance(new BigDecimal(2000));
        lstAccountDTO.add(accountDTO);
        return lstAccountDTO;
    }
    private List<Account> getAccountEntity(){
        List<Account> lstAccountDTO = new ArrayList<>();
        Account account =new Account();
        account.setIdAccount(Long.valueOf("1"));
        account.setAccountType("Ahorros");
        account.setAccountNumber("2901020304");
        account.setStatus(true);
        account.setInitialBalance(new BigDecimal(2000));
        lstAccountDTO.add(account);
        return lstAccountDTO;
    }
}
