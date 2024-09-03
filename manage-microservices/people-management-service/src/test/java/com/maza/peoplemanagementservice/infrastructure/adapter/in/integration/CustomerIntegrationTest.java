package com.maza.peoplemanagementservice.infrastructure.adapter.in.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maza.peoplemanagementservice.domain.dto.CustomerDTO;
import com.maza.peoplemanagementservice.domain.dto.request.CustomerRequestDTO;
import com.maza.peoplemanagementservice.domain.dto.request.Gender;
import com.maza.peoplemanagementservice.domain.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void createTransactionTest() throws Exception {

        CustomerRequestDTO customer = buildRequest();
        byte[] customerJson = objectMapper.writeValueAsBytes(customer);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", equalTo("ok")))
                .andDo(print());
    }
    @Test
    public void getCustomerTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
    private CustomerRequestDTO buildRequest(){
        CustomerRequestDTO customer = new CustomerRequestDTO();
        customer.setName("Christian Maza");
        customer.setPassword("123456789");
        customer.setStatus(true);
        customer.setAge(33);
        customer.setAddress("Catacocha");
        customer.setPhone("091011121");
        customer.setIdCard("1105060772");
        customer.setGender(Gender.MASCULINO);
        return customer;
    }
}