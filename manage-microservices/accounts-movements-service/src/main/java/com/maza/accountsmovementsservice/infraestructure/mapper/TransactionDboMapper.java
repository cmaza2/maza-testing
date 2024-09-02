package com.maza.accountsmovementsservice.infraestructure.mapper;


import com.maza.accountsmovementsservice.domain.entities.Transaction;
import com.maza.accountsmovementsservice.domain.entities.Transactions;
import com.maza.accountsmovementsservice.infraestructure.entities.TransactionEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionDboMapper {
    @Mapping(source = "idTransaction", target = "idTransaction")
    @Mapping(source = "idAccount", target = "idAccount")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "transactionType", target = "transactionType")
    @Mapping(source = "value", target = "value")
    @Mapping(source = "balance", target = "balance")
    TransactionEntity toDbo(Transaction domain);
    @InheritInverseConfiguration
    Transaction toDomain(TransactionEntity transactionEntity);
    @InheritInverseConfiguration
    Transactions toDomainTransactions(TransactionEntity transactionEntity);
}
