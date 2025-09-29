package com.example.producer_service.controller;

import com.example.producer_service.service.KafkaProducerService;
import com.example.shared_module.MessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
public class ProducerController {

    @Autowired
    private KafkaProducerService producerService;

    /**
     * Il messagio da inviare avrà URL:
     *
     * http://localhost:8081/send?message=Messaggio_che_sto_inviando
     *
     * @param messageRequest
     * @return
     */
    @PostMapping("/send")
    public String sendMessage(@RequestBody MessageData messageRequest) {
        producerService.sendMessage(messageRequest);
        return "Message sent with success!";
    }
}
