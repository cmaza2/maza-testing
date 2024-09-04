package com.maza.peoplemanagementservice.infrastructure.adapter.unit;

import com.maza.peoplemanagementservice.domain.dto.request.Gender;
import com.maza.peoplemanagementservice.infrastructure.adapter.repository.CustomerRepository;
import com.maza.peoplemanagementservice.infrastructure.entity.CustomerEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @Test
    void saveOrUpdate() {
        List<CustomerEntity> lstCustomer=getCustomers();

        when(customerRepository.findAll()).thenReturn(lstCustomer);

        // Llamar al metodo del servicio
        List<CustomerEntity> customers = customerRepository.findAll();

        // Verificar el resultado
        assertEquals(2, customers.size());
        assertEquals("0910111210", customers.get(0).getPhone());
        assertEquals("0910111211", customers.get(1).getPhone());
        // Verificar que el m�todo del repositorio fue invocado
        verify(customerRepository, times(1)).findAll();
    }
    private List<CustomerEntity> getCustomers(){
        List<CustomerEntity> lstCustomer = new ArrayList<>();
        for(int i=0;i<2;i++) {
            CustomerEntity customerA = new CustomerEntity();
            customerA.setName("Christian Maza"+i);
            customerA.setPassword("12345678"+i);
            customerA.setStatus(true);
            customerA.setAge(31+i);
            customerA.setAddress("Cuenca");
            customerA.setPhone("091011121"+i);
            customerA.setIdCard("110405069"+i);
            customerA.setIdCustomer(Long.valueOf(i+1));
            customerA.setGender(Gender.MASCULINO);
            lstCustomer.add(customerA);
        }
        return lstCustomer;
    }
}