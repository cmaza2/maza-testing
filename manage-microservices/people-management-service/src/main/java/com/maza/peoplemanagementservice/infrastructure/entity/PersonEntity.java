package com.maza.peoplemanagementservice.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;


@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name ="tpersons")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPerson;
    @Pattern(regexp = "^[a-zA-Z]+$", message = "The name must only contain letters")
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String gender;
    @Pattern(regexp = "^[0-9]+$", message = "The age must only contain numbers")
    @Column(nullable = false)
    private int age;
    @Pattern(regexp = "^[0-9]+$", message = "The id card must only contain numbers")
    @Column(nullable = false,unique = true,length = 10)
    private String idCard;
    @Column(nullable = false,length = 30)
    private String address;
    @Column(nullable = false,length =10 )
    @Pattern(regexp = "^[0-9]+$", message = "The phone number only contain numbers")
    private String phone;
}
