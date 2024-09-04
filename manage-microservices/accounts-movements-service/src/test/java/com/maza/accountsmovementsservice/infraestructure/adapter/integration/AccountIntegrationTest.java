package com.maza.accountsmovementsservice.infraestructure.adapter.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maza.accountsmovementsservice.domain.dto.request.AccountRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AccountIntegrationTest extends SetupDockerContainerTest{

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createAccounting() throws Exception {
        AccountRequestDTO accountRequestDTO = buildTransactionRequest();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(accountRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", equalTo("ok")))
                .andDo(print());
    }
    @Test
    public void createAccountingError() throws Exception {
        AccountRequestDTO accountRequestDTO = buildIncomplateRequest();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(accountRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", equalTo("error")))
                .andDo(print());
    }

    @Test
    public void getAccounts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cuentas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    private AccountRequestDTO buildTransactionRequest() {
       AccountRequestDTO accountRequestDTO = new AccountRequestDTO();
        accountRequestDTO.setAccountNumber("2905060708");
        accountRequestDTO.setCustomer("1101020304");
        accountRequestDTO.setStatus(true);
        accountRequestDTO.setAccountType("Ahorros");
        accountRequestDTO.setInitialBalance(new BigDecimal(2000));
        return accountRequestDTO;
    }
    private AccountRequestDTO buildIncomplateRequest() {
        AccountRequestDTO accountRequestDTO = new AccountRequestDTO();
        accountRequestDTO.setAccountNumber("2905060708");
        accountRequestDTO.setCustomer("1101020304");
        accountRequestDTO.setStatus(true);
        return accountRequestDTO;
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}