package com.maza.accountsmovementsservice.aplication.mapper;


import com.maza.accountsmovementsservice.domain.dto.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountDtoMapper {
    @Mapping(source = "idAccount", target = "idAccount")
    @Mapping(source = "idCustomer", target = "idCustomer")
    @Mapping(source = "accountNumber", target = "accountNumber")
    @Mapping(source = "accountType", target = "accountType")
    @Mapping(source = "initialBalance", target = "initialBalance")
    @Mapping(source = "status", target = "status")
    Account toDto(com.maza.accountsmovementsservice.domain.entities.Account domain);
}
