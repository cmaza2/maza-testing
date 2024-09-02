package com.maza.peoplemanagementservice.domain.entities;

import lombok.Data;

@Data
public class Customer extends Person{

    Long idCustomer;
    private String password;
    private boolean status;

}
