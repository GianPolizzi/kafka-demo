package com.example.consumer_service.listener;

import com.example.consumer_service.utils.Constants;
import com.example.shared_module.MessageData;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerService {

    @KafkaListener(topics = Constants.TOPIC, groupId = "my-group")
    public void listen(MessageData message) {
        System.out.println("Message received: " + message.toString());
    }
}
