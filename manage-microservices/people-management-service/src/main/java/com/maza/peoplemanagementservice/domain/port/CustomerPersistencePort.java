package com.maza.peoplemanagementservice.domain.port;

import com.maza.peoplemanagementservice.domain.entities.Customer;

import java.util.List;

public interface CustomerPersistencePort {
    Customer save(Customer customer);
    Customer findById(Long id);
    void deleteById(Long id);
    List<Customer> findAll();
    Customer update(Customer customer);
    Customer findByIdentification(String id);
}
