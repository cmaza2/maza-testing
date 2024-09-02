package com.maza.peoplemanagementservice.domain.dto.request;

import lombok.Data;

@Data
public class CustomerRequestDTO {

    private String name;
    private String gender;
    private int age;
    private String idCard;
    private String address;
    private String phone;
    private String password;
    private boolean status;
}
