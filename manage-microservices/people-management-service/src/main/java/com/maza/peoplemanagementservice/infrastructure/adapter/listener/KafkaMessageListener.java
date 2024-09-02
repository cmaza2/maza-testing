package com.maza.peoplemanagementservice.infrastructure.adapter.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaMessageListener {
    @Value("${kafka.topic.name}")
    private String topicName;

    /**
     * Method that process the message from a producer an call a manager-consumer microservice
     *
     * @param message Data to send
     */

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "maza-id")
    public void listen(String message) {
       log.info("Received message from topic " + topicName + ": " + message);

    }
}