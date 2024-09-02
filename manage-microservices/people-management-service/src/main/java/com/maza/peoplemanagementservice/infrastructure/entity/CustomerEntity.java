package com.maza.peoplemanagementservice.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tcustomers")
@PrimaryKeyJoinColumn(referencedColumnName = "idPerson")
public class CustomerEntity extends PersonEntity {

    Long idCustomer;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean status;

}
