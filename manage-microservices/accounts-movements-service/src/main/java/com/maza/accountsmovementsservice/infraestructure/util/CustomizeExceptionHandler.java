package com.maza.accountsmovementsservice.infraestructure.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class CustomizeExceptionHandler {

    /**
     * Method that handle and exception ocurred
     * @param ex  exception
     *
     * @return Json message of error.
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseObject> handleException(Exception ex) {
        ResponseObject responseObject = new ResponseObject("error",ex.getMessage(),"");
        return ResponseEntity.ok(responseObject);
    }
}
