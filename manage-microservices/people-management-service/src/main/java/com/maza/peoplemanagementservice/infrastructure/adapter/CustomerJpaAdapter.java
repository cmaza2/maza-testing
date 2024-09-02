package com.maza.peoplemanagementservice.infrastructure.adapter;

import com.maza.peoplemanagementservice.application.mapper.CustomerDtoMapper;
import com.maza.peoplemanagementservice.domain.entities.Customer;
import com.maza.peoplemanagementservice.domain.port.CustomerPersistencePort;
import com.maza.peoplemanagementservice.infrastructure.adapter.mapper.CustomerDboMapper;
import com.maza.peoplemanagementservice.infrastructure.adapter.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerJpaAdapter implements CustomerPersistencePort {
    private final CustomerRepository customerRepository;
    private final CustomerDboMapper customerDboMapper;
    private static final String ERROR_MESSAGE="PERSONA NO ENCONTRADA";

    public CustomerJpaAdapter(CustomerRepository customerRepository, CustomerDboMapper customerDboMapper) {
        this.customerRepository = customerRepository;
        this.customerDboMapper = customerDboMapper;
    }
    @Override
    public Customer save(Customer customerDomain) {
        var userToSave = customerDboMapper.toDbo(customerDomain);
        var userSaved = customerRepository.save(userToSave);
        return customerDboMapper.toDomain(userSaved);

    }
    @Override
    public Customer update(Customer customerDomain) {
        var userToSave = customerDboMapper.toDbo(customerDomain);
        var userSaved = customerRepository.save(userToSave);
        return customerDboMapper.toDomain(userSaved);
    }
    @Override
    public Customer findById(Long id) {
        var optionalUser = customerRepository.findById(id);
        if (optionalUser.isEmpty()){
            throw new RuntimeException(ERROR_MESSAGE+" "+ id);
        }

        return customerDboMapper.toDomain(optionalUser.get());
    }


    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(customerDboMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Customer findByIdentification(String id) {
        var optionalCustomer = customerRepository.findCustomerByIdCard(id);
        if (optionalCustomer.isEmpty()){
            throw new RuntimeException(ERROR_MESSAGE+" "+ id);
        }

        return customerDboMapper.toDomain(optionalCustomer.get());
    }
}
