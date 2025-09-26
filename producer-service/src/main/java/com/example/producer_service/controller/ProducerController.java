package com.example.producer_service.controller;

import com.example.producer_service.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @Autowired
    private KafkaProducerService producerService;

    /**
     * Il messagio da inviare avrà URL:
     *
     * http://localhost:8081/send?message=Messaggio_che_sto_inviando
     *
     * @param message
     * @return
     */
    @GetMapping("/send")
    public String sendMessage(@RequestParam("message") String message) {
        producerService.sendMessage(message);
        return "Message sent with success!";
    }
}
