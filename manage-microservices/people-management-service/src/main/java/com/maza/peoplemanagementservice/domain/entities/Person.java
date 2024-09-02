package com.maza.peoplemanagementservice.domain.entities;

import com.maza.peoplemanagementservice.domain.dto.request.Gender;
import lombok.Data;
@Data
public class Person {
    private Long idPerson;
    private String name;
    private Gender gender;
    private int age;
    private String idCard;
    private String address;
    private String phone;
}
