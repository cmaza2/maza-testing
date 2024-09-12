package com.maza.peoplemanagementservice.application.service;

import com.maza.peoplemanagementservice.application.mapper.CustomerDtoMapper;
import com.maza.peoplemanagementservice.application.mapper.CustomerRequestMapper;
import com.maza.peoplemanagementservice.application.usecases.CustomerUseCase;
import com.maza.peoplemanagementservice.domain.port.CustomerPersistencePort;
import com.maza.peoplemanagementservice.domain.dto.CustomerDTO;
import com.maza.peoplemanagementservice.domain.dto.request.CustomerRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerService implements CustomerUseCase {

    private final CustomerPersistencePort customerPersistencePort;
    private final CustomerRequestMapper customerRequestMapper;
    private final CustomerDtoMapper customerDtoMapper;
    private static final String ERROR_MESSAGE="USUARIO NO ENCONTRADO";

    @Autowired
    public CustomerService(final CustomerPersistencePort customerPersistencePort,
                           final CustomerRequestMapper customerRequestMapper,
                           final CustomerDtoMapper customerDtoMapper) {
        this.customerPersistencePort = customerPersistencePort;
        this.customerRequestMapper = customerRequestMapper;
        this.customerDtoMapper = customerDtoMapper;

    }


    /**
     * Method that calls the repository and create or update a customer
     *
     * @param customerRequestDTO Customer.
     * @return Customer information
     */
    @Override
    public CustomerDTO save(CustomerRequestDTO customerRequestDTO) {
        var customer = customerRequestMapper.toDomain(customerRequestDTO);
        var customerCreated = customerPersistencePort.save(customer);
        var customerResponseDTO = customerDtoMapper.toDto(customerCreated);
        log.info("Trama de respuesta crear persona: {}",customerResponseDTO);
        return customerResponseDTO;
    }
    /**
     * Method that calls the port and create or update a customer
     *
     * @param customerDTO Customer.
     * @return Customer information
     */
    @Override
    public CustomerDTO update(Long id,CustomerRequestDTO customerDTO) {
        var customerExist=findById(id);
        var customer = customerRequestMapper.toDomain(customerDTO);
        customer.setIdPerson(id);
        customer.setIdCustomer(customerExist.getIdCustomer());
        var customerUpdate = customerPersistencePort.update(customer);
        var customerMapper= customerDtoMapper.toDto(customerUpdate);
        log.info("Trama de respuesta actualizar persona por id: id={},tramaRespuesta={}",id,customerMapper);
        return customerMapper;
    }

    /**
     * Method that calls the port and return a customer by id
     *
     * @param id Id of customer.
     * @return Customer information
     */
    @Override
    public CustomerDTO findById(Long id) {
        var customer = customerPersistencePort.findById(id);
        var customerResponseDTO = customerDtoMapper.toDto(customer);
        log.info("Trama de respuesta buscar persona por id: id={},trama={}",id,customerResponseDTO);
        return customerResponseDTO;
    }

    /**
     * Method that calls the port and list all customers
     *
     * @return List of customers.
     */
    @Override
    public List<CustomerDTO> findAll() {
        var users = customerPersistencePort.findAll();
        return users
                .stream()
                .map(customerDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Method that calls the port and deletes a customer
     *
     * @param id Id of account.
     */
    @Override
    public void deleteById(Long id) {
        var customer= customerPersistencePort.findById(id);
        if(!customer.getIdCard().isEmpty()){
            throw new RuntimeException(String.format(ERROR_MESSAGE, customer.getName()));
        }
        customerPersistencePort.deleteById(id);
        log.info("Trama de respuesta, id de persona eliminada: {}",id);
    }

    /**
     * Method that calls the port and return a customer by document id
     *
     * @param idNumber Document id of customer.
     * @return Customer information
     */
    @Override
    public CustomerDTO findByIdentification(String idNumber) {
        var customer = customerPersistencePort.findByIdentification(idNumber);
        var customerResponseDTO = customerDtoMapper.toDto(customer);
        log.info("Trama de respuesta buscar persona por identificacion {} : {}",idNumber,customerResponseDTO);
        return customerResponseDTO;
    }
}
