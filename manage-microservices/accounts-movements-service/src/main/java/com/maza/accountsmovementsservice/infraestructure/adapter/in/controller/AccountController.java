package com.maza.accountsmovementsservice.infraestructure.adapter.in.controller;

import com.maza.accountsmovementsservice.domain.dto.AccountDTO;
import com.maza.accountsmovementsservice.domain.dto.request.AccountRequestDTO;
import com.maza.accountsmovementsservice.infraestructure.util.HelperUtilies;
import com.maza.accountsmovementsservice.infraestructure.util.ResponseObject;

import com.maza.accountsmovementsservice.aplication.services.AccountServices;
import com.maza.accountsmovementsservice.domain.entities.Account;
import com.maza.accountsmovementsservice.infraestructure.adapter.out.GetCustomerService;
import com.maza.accountsmovementsservice.infraestructure.dto.CustomerDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cuentas")
@Slf4j
@Api(tags = "AccountController", description = "Operations related to accounts")
public class AccountController {
    @Autowired
    private AccountServices accountServices;
    @Autowired
    private GetCustomerService customerService;

    @GetMapping
    @ApiOperation(value = "getAccounts", notes = "List a register accounts")
    public ResponseEntity<List<AccountDTO>> getAccounts() {
        List<AccountDTO> customers = accountServices.getAccounts();
        return ResponseEntity.ok(customers);
    }

    @PostMapping
    @ApiOperation(value = "createAccount", notes = "Register a new account")
    public ResponseEntity<ResponseObject> createAccount(@RequestBody AccountRequestDTO accountRequestDTO) throws Exception {
        CustomerDTO customerDTO = customerService.getCustomer(accountRequestDTO.getCustomer());
        AccountDTO createAccount = accountServices.create(accountRequestDTO,customerDTO);
       return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok", "Account created sucesfully", createAccount));

    }

    @PutMapping("/{id}")
    @ApiOperation(value = "updateAccount", notes = "Update account by Id")
    public ResponseEntity<ResponseObject> updateAccount(@PathVariable Long id, @RequestBody AccountRequestDTO accountRequestDTO) throws Exception {
        CustomerDTO customerDTO = customerService.getCustomer(accountRequestDTO.getCustomer());
        return ResponseEntity.ok(new ResponseObject("ok", "Account update sucesfully",  accountServices.update(id,customerDTO.getIdCustomer(),accountRequestDTO)));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "deleteAccount", notes = "Delete account by Id")
    public ResponseEntity<ResponseObject> deleteAccount(@PathVariable Long id) {
        accountServices.deleteAccount(id);
        return ResponseEntity.ok(new ResponseObject("ok", "Account delete sucesfully", ""));
    }
}
