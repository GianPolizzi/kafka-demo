package com.example.producer_service.service;

import com.example.shared_module.Constants;
import com.example.shared_module.MessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, MessageData> kafkaTemplate;

    public void sendMessage(MessageData message) {
        String uniqueId = UUID.randomUUID().toString();
        // In questo specifico caso, l'uso di UUID.randomUUID() come chiave garantirà che ogni messaggio vada su una
        // partizione casuale. Questo è utile se si vuole bilanciare il carico in modo uniforme tra le partizioni.
        // Se invece volessi garantire l'ordine per un gruppo specifico (ad esempio: tutti i messaggi dello stesso sender),
        // dovrei usare il campo sender come chiave Kafka.
        //message.setId(message.getSender());
        message.setId(uniqueId);
        message.setTimestamp(LocalDateTime.now());
        String kafkaKeyMessage = message.getSender();
        System.out.println("Sending message: [" + message.getMessage() + "] with key: [" + kafkaKeyMessage + "]  to topic: " + Constants.TOPIC);
        this.kafkaTemplate.send(Constants.TOPIC, kafkaKeyMessage, message);
    }
}
