package com.maza.accountsmovementsservice.infraestructure.adapter.in.controller;


import com.maza.accountsmovementsservice.aplication.services.AccountServices;
import com.maza.accountsmovementsservice.aplication.services.TransactionServices;
import com.maza.accountsmovementsservice.domain.dto.AccountDTO;
import com.maza.accountsmovementsservice.domain.dto.TransactionDTO;
import com.maza.accountsmovementsservice.domain.dto.request.TransactionRequestDTO;
import com.maza.accountsmovementsservice.infraestructure.util.HelperUtilies;
import com.maza.accountsmovementsservice.infraestructure.util.ResponseObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maza.accountsmovementsservice.infraestructure.dto.CustomerDTO;
import com.maza.accountsmovementsservice.infraestructure.dto.TransactionsDTO;
import com.maza.accountsmovementsservice.aplication.usecases.AccountsUseCase;
import com.maza.accountsmovementsservice.domain.entities.Account;
import com.maza.accountsmovementsservice.domain.entities.Transaction;
import com.maza.accountsmovementsservice.infraestructure.adapter.out.GetCustomerService;
import com.maza.accountsmovementsservice.infraestructure.util.TypeMovement;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movimientos")
@Tag(name = "MovementController", description = "Operations related to transactions carried out by accounts")
@Slf4j
public class TransactionController {

    private TransactionServices transactionServices;

    private AccountServices accountServices;
    private GetCustomerService customerService;
    private KafkaTemplate kafkaTemplate;
    @Autowired
    public TransactionController(TransactionServices transactionServices,
                                 AccountServices accountServices,
                                 GetCustomerService customerService,
                                 KafkaTemplate kafkaTemplate) {
        this.transactionServices = transactionServices;
        this.accountServices = accountServices;
        this.customerService = customerService;
        this.kafkaTemplate = kafkaTemplate;
    }


    @GetMapping
    @ApiOperation(value = "getTransactions", notes = "gets all transactions made")
    public ResponseEntity<List<TransactionDTO>> getTransactions() {
        List<TransactionDTO> transactions = transactionServices.getTransactions();
        return ResponseEntity.ok(transactions);
    }

    @PostMapping
    @ApiOperation(value = "createTransaction", notes = "Register a new transaction")
    public ResponseEntity<Object> createTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) throws Exception {
        TransactionDTO createdMovement = transactionServices.createTransaction(transactionRequestDTO);
        ResponseObject responseObject = new ResponseObject("ok", "Transaction created sucesfully", createdMovement);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseObject);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "updatetransaction", notes = "Update Transaction by id")
    public ResponseEntity<ResponseObject> updatetransaction(@PathVariable Long id, @RequestBody TransactionRequestDTO movementRequestDTO) {
        return ResponseEntity.ok(new ResponseObject("ok", "Transaction with id " + id + " updated succesfully", transactionServices.updateTransaction(id,movementRequestDTO)));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "deleteTransaction", notes = "Delete transaction by id")
    public ResponseEntity<ResponseObject> deleteTransaction(@PathVariable Long id) {
        transactionServices.deleteTransaction(id);
        return ResponseEntity.ok(new ResponseObject("ok", "Transaction with id " + id + " deleted correctly", ""));
    }

    @GetMapping("/reportes")
    @ApiOperation(value = "getTransactionByUserAndDate", notes = "Gets all transactions made by date range and customer")
    public ResponseEntity<List<TransactionsDTO>> getTransactionByUserAndDate(@RequestParam String fechaInicial, @RequestParam String fechaFinal, @RequestParam String cliente) throws Exception {
        CustomerDTO customer =  customerService.getCustomer(cliente);
        return ResponseEntity.ok(transactionServices.getMovementsByUserAndDate( LocalDate.parse(fechaInicial),   LocalDate.parse(fechaFinal),customer));
    }

    @GetMapping("/enviar_reporte")
    @ApiOperation(value = "getTransactionByUserAndDate", notes = "Gets all transactions made by date range and customer")
    public ResponseEntity<List<TransactionsDTO>> sendAccountStatus(@RequestParam String fechaInicial, @RequestParam String fechaFinal, @RequestParam String cliente) throws Exception {
        CustomerDTO customer = customerService.getCustomer(cliente);
        List<TransactionsDTO> customers = transactionServices.getMovementsByUserAndDate(LocalDate.parse(fechaInicial), LocalDate.parse(fechaFinal), customer);
        //kafkaTemplate.send();
        return ResponseEntity.ok(customers);
    }

}
