package com.maza.peoplemanagementservice.infrastructure.adapter.integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.time.Duration;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class SetupDockerContainerTest {

    private static DockerComposeContainer<?> composeContainer;
    @BeforeAll
    public static void setUp() {
        // Define the Docker Compose container
        composeContainer = new DockerComposeContainer<>(new File("src/test/resources/docker-compose.yml"))
                .withExposedService("cont-db_1", 3306, Wait.forListeningPort().withStartupTimeout(Duration.ofMinutes(2)));
        composeContainer.start();
    }
    @AfterAll
    public static void tearDown() {
        if (composeContainer != null) {
            composeContainer.stop();
        }
    }
}
