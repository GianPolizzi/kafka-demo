package com.example.consumer_service.listener;

import com.example.shared_module.Constants;
import com.example.shared_module.MessageData;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerListener {

    @KafkaListener(topics = Constants.TOPIC, groupId = "my-group")
    public void listen(@Payload MessageData message) {

        System.out.println("Message received: " + message.toString());

        // *** PUNTO DI TEST PER L'ERRORE ***
        // Simula un errore di logica se il messaggio ha un certo contenuto oppure
        // il  mittente fa parte di una black list
        if (message.getSender().equalsIgnoreCase("fail-me")) {
            throw new RuntimeException("Simulated processing error for DLT test.");
        }

        // ** LOGICA DI BUSINESS **
        System.out.println("------------------------------------------");
        System.out.println("CONSUMER RECEIVED NEW MESSAGE (FORMATTED):");
        System.out.println("------------------------------------------");
        System.out.println("ID:   " + message.getId());
        System.out.println("Content:   " + message.getMessage());
        System.out.println("Sender:    " + message.getSender());
        System.out.println("Timestamp: " + message.getTimestamp());
        System.out.println("------------------------------------------\n");
    }

    @KafkaListener(topics = Constants.TOPIC_DLT, groupId = "dlt-group")
    public void listenDLT(@ Payload MessageData message) {
        System.err.println("\n#################################################");
        System.err.println("!!! DLT RECEIVED FAILED MESSAGE !!!");
        System.err.println("Original Message Content: " + message.getMessage());
        System.err.println("Sender that caused the failure: " + message.getSender());
        System.err.println("#################################################\n");
        // Qui si implementerebbero azioni come: salvare in un DB di errore, inviare una notifica email, ecc.
    }
}
