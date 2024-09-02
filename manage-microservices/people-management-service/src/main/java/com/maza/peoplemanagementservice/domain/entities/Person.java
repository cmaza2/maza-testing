package com.maza.peoplemanagementservice.domain.entities;

import lombok.Data;
@Data
public class Person {
    private Long idPerson;
    private String name;
    private String gender;
    private int age;
    private String idCard;
    private String address;
    private String phone;
}
