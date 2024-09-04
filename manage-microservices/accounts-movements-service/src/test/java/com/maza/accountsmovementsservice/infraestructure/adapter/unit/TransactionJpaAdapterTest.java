package com.maza.accountsmovementsservice.infraestructure.adapter.unit;

import com.maza.accountsmovementsservice.domain.entities.Transaction;
import com.maza.accountsmovementsservice.domain.repository.TransactionRepository;
import com.maza.accountsmovementsservice.infraestructure.adapter.TransactionJpaAdapter;
import com.maza.accountsmovementsservice.infraestructure.entities.TransactionEntity;
import com.maza.accountsmovementsservice.infraestructure.mapper.TransactionDboMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TransactionJpaAdapterTest {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionJpaAdapter transactionJpaAdapter;
    @Mock
    private TransactionDboMapper transactionDboMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void save() {
        TransactionEntity transactionEntity = getTransactionEntity();
        Transaction transactionDomain=getTransaction();
        when(transactionDboMapper.toDbo(transactionDomain)).thenReturn(transactionEntity);
        when(transactionRepository.save(transactionEntity)).thenReturn(transactionEntity);
        when(transactionDboMapper.toDomain(transactionEntity)).thenReturn(transactionDomain);
        // Llamar al método del servicio
        Transaction createdTransaction = transactionJpaAdapter.save(transactionDomain);
        assertNotNull(createdTransaction);
        assertEquals("Retiro", createdTransaction.getTransactionType());
        assertEquals(1, createdTransaction.getIdAccount());

        // Verificar la interacción con el repositorio
        verify(transactionRepository).save(transactionEntity);
    }
    private TransactionEntity getTransactionEntity(){
        TransactionEntity transactionEntity =new TransactionEntity();
        transactionEntity.setTransactionType("Retiro");
        transactionEntity.setDate(LocalDate.now());
        transactionEntity.setIdAccount(Long.valueOf(1));
        transactionEntity.setValue(new BigDecimal(400));
        transactionEntity.setBalance(new BigDecimal(1600));
        return transactionEntity;
    }
    private Transaction getTransaction(){
        Transaction transaction =new Transaction();
        transaction.setTransactionType("Retiro");
        transaction.setDate(LocalDate.now());
        transaction.setIdAccount(Long.valueOf(1));
        transaction.setValue(new BigDecimal(400));
        transaction.setBalance(new BigDecimal(1600));
        return transaction;
    }
}