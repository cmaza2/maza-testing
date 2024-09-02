package com.maza.peoplemanagementservice.infrastructure.adapter.repository;

import com.maza.peoplemanagementservice.infrastructure.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findCustomerByIdCard(String identificationNumber);
}
