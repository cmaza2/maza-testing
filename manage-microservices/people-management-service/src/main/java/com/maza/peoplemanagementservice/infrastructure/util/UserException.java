package com.maza.peoplemanagementservice.infrastructure.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private HttpStatus errorCode;
    private String errorMessage;
}
