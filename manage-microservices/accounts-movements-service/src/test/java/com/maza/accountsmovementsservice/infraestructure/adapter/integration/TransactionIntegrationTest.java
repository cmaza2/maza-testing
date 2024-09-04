package com.maza.accountsmovementsservice.infraestructure.adapter.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.maza.accountsmovementsservice.domain.dto.request.TransactionRequestDTO;
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
import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TransactionIntegrationTest extends SetupDockerContainerTest{
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createTransaction() throws Exception {
        TransactionRequestDTO transactionRequestDTO = buildTransactionRequest();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(transactionRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", equalTo("ok")))
                .andDo(print());
    }
    @Test
    void createTransactionWithError() throws Exception {
        TransactionRequestDTO transactionRequestDTO = buildIncomplateRequest();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(transactionRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", equalTo("error")))
                .andDo(print());
    }

    @Test
    public void getReportTransaction() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movimientos/reportes")
                        .queryParam("fechaInicial","2024-09-01")
                        .queryParam("fechaFinal","2024-09-30")
                        .queryParam("cliente","1101020304")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    private TransactionRequestDTO buildTransactionRequest() {
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();
        transactionRequestDTO.setAccount("2901020304");
        transactionRequestDTO.setTransactionType("Deposito");
        transactionRequestDTO.setDate(LocalDate.now());
        transactionRequestDTO.setValue(new BigDecimal("500"));
        return transactionRequestDTO;
    }

    private TransactionRequestDTO buildIncomplateRequest() {
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();
        transactionRequestDTO.setTransactionType("Deposito");
        transactionRequestDTO.setDate(LocalDate.now());
        transactionRequestDTO.setValue(new BigDecimal("500"));
        return transactionRequestDTO;
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(object);
    }
}