package com.maza.peoplemanagementservice.infrastructure.adapter.in;

import com.maza.peoplemanagementservice.application.usecases.CustomerUseCase;
import com.maza.peoplemanagementservice.domain.dto.CustomerDTO;
import com.maza.peoplemanagementservice.domain.dto.request.CustomerRequestDTO;
import com.maza.peoplemanagementservice.infrastructure.util.ResponseObject;
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
@RequestMapping("/api/clientes")
@Slf4j
@Api(tags = "CustomerController", description = "Operations related to customers")
public class CustomerController {
    private CustomerUseCase customerUseCase;

    @Autowired
    public CustomerController(CustomerUseCase customerService) {
        this.customerUseCase = customerService;
    }

    @GetMapping
    @ApiOperation(value = "getCustomers", notes = "List a register customers")
    public ResponseEntity<List<CustomerDTO>> getCustomers(){
        return ResponseEntity.ok(customerUseCase.findAll());
    }
    @PostMapping
    @ApiOperation(value = "createCustomer", notes = "Register a new customer")
    public ResponseEntity<ResponseObject> createCustomer(@Valid @RequestBody CustomerRequestDTO customerRequestDTO){
        log.info("Trama de entrada crear cliente: {}",customerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok","Customer created sucesfully",customerUseCase.save(customerRequestDTO)));
    }
    @PutMapping("/{id}")
    @ApiOperation(value = "updateCustomer", notes = "Update customer by Id")
    public ResponseEntity<ResponseObject> updateCustomer(@Valid @PathVariable Long id, @RequestBody CustomerRequestDTO customerRequestDTO) {
        log.info("Id de persona a actualizar: {}",id);
        return ResponseEntity.ok(new ResponseObject("ok","Customer updated sucesfully", customerUseCase.update(id,customerRequestDTO)));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "deleteCustomer", notes = "Delete customer by Id")
    public  ResponseEntity<ResponseObject> deleteCustomer(@PathVariable Long id){
        log.info("Id de persona a eliminar: {}",id);
        customerUseCase.deleteById(id);
        return ResponseEntity.ok(new ResponseObject("ok","Customer deleted sucesfully", ""));

    }
    @GetMapping("/customers")
    @ApiOperation(value = "findClienByIdCard", notes = "Find client by Id Number")
    public CustomerDTO findClienById(@RequestParam String id){
        log.info("Buscar persona por cedula {}",id);
        return  customerUseCase.findByIdentification(id);
    }
    @PostMapping("/statments")
    @ApiOperation(value = "statments", notes = "Send message to kafka provider")
    public void findClienByName(@RequestBody String message){
        log.info("Estado de cuenta de forma asincrona: "+message);
    }
}
