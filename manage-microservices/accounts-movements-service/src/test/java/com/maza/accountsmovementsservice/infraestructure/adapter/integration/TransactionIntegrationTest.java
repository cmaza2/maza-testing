package com.maza.accountsmovementsservice.infraestructure.adapter.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.maza.accountsmovementsservice.domain.dto.request.TransactionRequestDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    private static DockerComposeContainer<?> composeContainer;

    @BeforeAll
    public static void setUp() {
        // Define the Docker Compose container
        composeContainer = new DockerComposeContainer<>(new File("src/test/resources/docker-compose.yml"))
               // .withExposedService("springboot-customer_1", 8080, Wait.forListeningPort().withStartupTimeout(Duration.ofMinutes(2)))
                .withExposedService("cont-db2_1", 8086, Wait.forListeningPort().withStartupTimeout(Duration.ofMinutes(2)));
                //.withExposedService("cont-db_1", 3306, Wait.forListeningPort().withStartupTimeout(Duration.ofMinutes(2)));
        composeContainer.start();
    }

    @AfterAll
    public static void tearDown() {
        if (composeContainer != null) {
            composeContainer.stop();
        }
    }

    @Test
    void createTransaction() throws Exception {
        TransactionRequestDTO transactionRequestDTO = buildTransactionRequest();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(transactionRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
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
                        .queryParam("cliente","1104637911")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    private TransactionRequestDTO buildTransactionRequest() {
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();
        transactionRequestDTO.setAccount("2901096464");
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