package com.maza.accountsmovementsservice.infraestructure.adapter.in.controller;

import com.maza.accountsmovementsservice.aplication.services.TransactionServices;
import com.maza.accountsmovementsservice.domain.dto.TransactionDTO;
import com.maza.accountsmovementsservice.domain.dto.request.TransactionRequestDTO;
import com.maza.accountsmovementsservice.infraestructure.util.ResponseObject;
import com.maza.accountsmovementsservice.infraestructure.dto.CustomerDTO;
import com.maza.accountsmovementsservice.infraestructure.dto.TransactionsDTO;
import com.maza.accountsmovementsservice.infraestructure.adapter.out.GetCustomerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@Tag(name = "MovementController", description = "Operations related to transactions carried out by accounts")
@Slf4j
public class TransactionController {

    private TransactionServices transactionServices;
    private GetCustomerService customerService;
    private KafkaTemplate kafkaTemplate;
    @Value("${kafka.topic.name}")
    private String topicName;

    @Autowired
    public TransactionController(TransactionServices transactionServices,
                                 GetCustomerService customerService,
                                 KafkaTemplate kafkaTemplate) {
        this.transactionServices = transactionServices;
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
    public ResponseEntity<Object> createTransaction(@Valid @RequestBody TransactionRequestDTO transactionRequestDTO) throws Exception {
        TransactionDTO createdMovement = transactionServices.createTransaction(transactionRequestDTO);
        ResponseObject responseObject = new ResponseObject("ok", "Transaction created sucesfully", createdMovement);
        log.info("Trama de respuesta: "+responseObject);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseObject);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "updatetransaction", notes = "Update Transaction by id")
    public ResponseEntity<ResponseObject> updatetransaction(@Valid @PathVariable Long id, @RequestBody TransactionRequestDTO movementRequestDTO) {
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
    public ResponseEntity<List<TransactionsDTO>> getTransactionByUserAndDate(@RequestParam String fechaInicial, @RequestParam String fechaFinal, @RequestParam String cliente){
        CustomerDTO customer =  customerService.getCustomer(cliente);
        return ResponseEntity.ok(transactionServices.getMovementsByUserAndDate( LocalDate.parse(fechaInicial),   LocalDate.parse(fechaFinal),customer));
    }

    @GetMapping("/enviar_reporte")
    @ApiOperation(value = "sendAccountStatus", notes = "Gets all transactions made by date range and customer")
    public ResponseEntity<List<TransactionsDTO>> sendAccountStatus(@RequestParam String fechaInicial, @RequestParam String fechaFinal, @RequestParam String cliente){
        CustomerDTO customer = customerService.getCustomer(cliente);
        List<TransactionsDTO> transactions = transactionServices.getMovementsByUserAndDate(LocalDate.parse(fechaInicial), LocalDate.parse(fechaFinal), customer);
        kafkaTemplate.send(topicName,transactions);
        return ResponseEntity.ok(transactions);
    }

}
