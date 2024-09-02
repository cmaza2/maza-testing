package com.maza.peoplemanagementservice.infrastructure.adapter.mapper;

import com.maza.peoplemanagementservice.domain.entities.Customer;
import com.maza.peoplemanagementservice.infrastructure.entity.CustomerEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerDboMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "age", target = "age")
    @Mapping(source = "idCard", target = "idCard")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "status", target = "status")

    CustomerEntity toDbo(Customer domain);
    @InheritInverseConfiguration
    Customer toDomain(CustomerEntity customerEntity);
}
