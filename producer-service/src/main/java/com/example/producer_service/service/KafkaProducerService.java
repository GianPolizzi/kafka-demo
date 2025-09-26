package com.example.producer_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "my-topic";

    public void sendMessage(String message) {
        System.out.println("Sending message: [" + message + "] to topic: " + TOPIC);
        this.kafkaTemplate.send(TOPIC, message);
    }
}
