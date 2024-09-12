package com.maza.accountsmovementsservice.infraestructure.adapter.controller;

import com.maza.accountsmovementsservice.aplication.usecases.AccountsUseCase;
import com.maza.accountsmovementsservice.domain.dto.Account;
import com.maza.accountsmovementsservice.domain.dto.request.AccountRequestDTO;
import com.maza.accountsmovementsservice.infraestructure.util.ResponseObject;
import com.maza.accountsmovementsservice.aplication.services.AccountServices;
import com.maza.accountsmovementsservice.infraestructure.adapter.out.GetCustomerService;
import com.maza.accountsmovementsservice.domain.dto.CustomerDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
@Slf4j
@Api(tags = "AccountController", description = "Operations related to accounts")
public class AccountController {
    private AccountsUseCase accountsUseCase;
    private GetCustomerService customerService;

    @Autowired
    public AccountController(AccountsUseCase accountsUseCase, GetCustomerService customerService) {
        this.accountsUseCase = accountsUseCase;
        this.customerService = customerService;
    }

    @GetMapping
    @ApiOperation(value = "getAccounts", notes = "List a register accounts")
    public ResponseEntity<List<Account>> getAccounts() {
        List<Account> accounts = accountsUseCase.getAccounts();
        log.info("Trama de salida: {}",accounts);
        return ResponseEntity.ok(accounts);
    }

    @PostMapping
    @ApiOperation(value = "createAccount", notes = "Register a new account")
    public ResponseEntity<ResponseObject> createAccount(@Valid @RequestBody AccountRequestDTO accountRequestDTO) {
        log.info("Trama de entrada: {}",accountRequestDTO);
        CustomerDTO customerDTO = customerService.getCustomer(accountRequestDTO.getCustomer());
        Account createAccount = accountsUseCase.create(accountRequestDTO, customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok", "Account created sucesfully", createAccount));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "updateAccount", notes = "Update account by Id")
    public ResponseEntity<ResponseObject> updateAccount(@Valid @PathVariable Long id, @RequestBody AccountRequestDTO accountRequestDTO){
        log.info("Id y trama a actualizar  id={}, trama={}",id,accountRequestDTO);
        CustomerDTO customerDTO = customerService.getCustomer(accountRequestDTO.getCustomer());
        return ResponseEntity.ok(new ResponseObject("ok", "Account update sucesfully", accountsUseCase.update(id, customerDTO.getIdCustomer(), accountRequestDTO)));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "deleteAccount", notes = "Delete account by Id")
    public ResponseEntity<ResponseObject> deleteAccount(@PathVariable Long id) {
        log.info("Id a eliminar: {}",id);
        accountsUseCase.deleteAccount(id);
        return ResponseEntity.ok(new ResponseObject("ok", "Account delete sucesfully", ""));
    }
    @GetMapping("/{identification}")
    @ApiOperation(value = "findAccountByIdentification", notes = "Find account by Identification Number")
    public ResponseEntity<ResponseObject> getAccountByIdentification(@Valid @PathVariable String identification){
        log.info("Identificacion a buscar {}:",identification);
        return ResponseEntity.ok(new ResponseObject("ok", "Account information recovery successfully", accountsUseCase.findByIdentification(identification)));
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "getAccountById", notes = "Find account by Id")
    public ResponseEntity<ResponseObject> getAccountById(@Valid @PathVariable Long id){
        log.info("Id a buscar  id={}",id);
        return ResponseEntity.ok(new ResponseObject("ok", "Account information recovery successfully", accountsUseCase.getAccountById(id)));
    }
    @GetMapping("/{account}")
    @ApiOperation(value = "getAccountInformationByAccount", notes = "Find account by accountNumber")
    public ResponseEntity<ResponseObject> getAccountInformationByAccount(@Valid @PathVariable String account){
        log.info("Cuenta a buscar  cuenta={}",account);
        return ResponseEntity.ok(new ResponseObject("ok", "Account information recovery successfully", accountsUseCase.getAccountInformation(account)));
    }
}
