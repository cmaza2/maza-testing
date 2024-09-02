package com.maza.peoplemanagementservice.infrastructure.entity;

import com.maza.peoplemanagementservice.domain.dto.request.Gender;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name ="tpersons")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPerson;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(nullable = false)
    private int age;
    @Column(nullable = false,unique = true,length = 10)
    private String idCard;
    @Column(nullable = false,length = 30)
    private String address;
    @Column(nullable = false,length =10 )
    private String phone;
}
