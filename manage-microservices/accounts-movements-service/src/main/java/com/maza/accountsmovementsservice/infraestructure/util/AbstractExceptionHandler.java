package com.maza.accountsmovementsservice.infraestructure.util;

import com.fasterxml.jackson.core.JsonParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
@Slf4j
public abstract class AbstractExceptionHandler {
    /**
     * Method that handle and exception ocurred
     *
     * @param ex exception
     * @return Json message of error.
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseObject> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ResponseObject responseObject = new ResponseObject("error", errors.toString(), "");
        log.error("Error in a request: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ResponseObject> handleSQLException(SQLException ex) {
        String errorMessage = String.format("Error de SQL: Código: %d, Estado: %s, Mensaje: %s",
                ex.getErrorCode(), ex.getSQLState(), ex.getMessage());
        ResponseObject responseObject = new ResponseObject("error", errorMessage.toString(), "");
        log.error("Error de SQL: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ResponseObject> handleJsonParseException(JsonParseException ex) {
        String errorMessage = ex.getMessage();
        ResponseObject responseObject = new ResponseObject("error", errorMessage, "");
        log.error("Error al parsear JSON: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseObject> handleNoSuchElementException(NoSuchElementException ex) {
        String errorMessage = ex.getMessage();
        ResponseObject responseObject = new ResponseObject("error", errorMessage, "");
        log.error("Error elemento no encontrado: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AccountException.class)
    public ResponseEntity<ResponseObject> handleUserException(AccountException ex) {
        String errorMessage = ex.getErrorMessage();
        ResponseObject responseObject = new ResponseObject("error", errorMessage, "");
        log.error("Error en las cuentas: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ResponseObject> handleUserException(TransactionException ex) {
        String errorMessage = ex.getErrorMessage();
        ResponseObject responseObject = new ResponseObject("error", errorMessage, "");
        log.error("Error en la transaccion {} {}", ex.getMessage(), ex);
        return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
    }

}

