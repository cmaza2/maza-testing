package com.maza.peoplemanagementservice.application.usecases;

import com.maza.peoplemanagementservice.domain.dto.CustomerDTO;
import com.maza.peoplemanagementservice.domain.dto.request.CustomerRequestDTO;

import java.util.List;

public interface CustomerUseCase {
    CustomerDTO save(CustomerRequestDTO customer);
    void deleteById(Long id);
    List<CustomerDTO> findAll();
    CustomerDTO findByIdentification(String id);
    CustomerDTO findById(Long id);
    CustomerDTO update(Long id,CustomerRequestDTO customer);

}
