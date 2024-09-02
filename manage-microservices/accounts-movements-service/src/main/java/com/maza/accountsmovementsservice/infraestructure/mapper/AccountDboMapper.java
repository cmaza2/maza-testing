package com.maza.accountsmovementsservice.infraestructure.mapper;

import com.maza.accountsmovementsservice.domain.entities.Account;
import com.maza.accountsmovementsservice.infraestructure.entities.AccountEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountDboMapper {
    @Mapping(source = "idCustomer", target = "idCustomer")
    @Mapping(source = "accountNumber", target = "accountNumber")
    @Mapping(source = "accountType", target = "accountType")
    @Mapping(source = "initialBalance", target = "initialBalance")
    @Mapping(source = "status", target = "status")
    AccountEntity toDbo(Account domain);
    @InheritInverseConfiguration
    Account toDomain(AccountEntity accountEntity);
}
