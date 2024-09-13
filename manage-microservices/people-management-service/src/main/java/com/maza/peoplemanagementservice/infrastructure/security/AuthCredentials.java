package com.maza.peoplemanagementservice.infrastructure.security;

import lombok.Data;

@Data
public class AuthCredentials {
    private String idNumber;
    private String password;
}
