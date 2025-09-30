package com.example.producer_service.service;

import com.example.shared_module.Constants;
import com.example.shared_module.MessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, MessageData> kafkaTemplate;

    public void sendMessage(MessageData message) {
        message.setTimestamp(LocalDateTime.now());
        System.out.println("Sending message: [" + message + "] to topic: " + Constants.TOPIC);
        this.kafkaTemplate.send(Constants.TOPIC, message);
    }
}
