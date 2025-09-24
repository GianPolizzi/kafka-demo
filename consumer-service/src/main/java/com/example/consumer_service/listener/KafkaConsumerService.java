package com.example.consumer_service.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerService {

    private static final String TOPIC = "my-topic";

    @KafkaListener(topics = TOPIC, groupId = "my-group")
    public void listen(String message) {
        System.out.println("Message received: " + message);
    }
}
