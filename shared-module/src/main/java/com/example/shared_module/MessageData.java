package com.example.shared_module;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Bean condiviso tra i microservizi per costruire il messaggio Kafka
 */
@Data
public class MessageData {

    private String id;
    private String message;
    private String sender;
    private LocalDateTime timestamp;

    @Override
    public String toString() {
        return "MessageData {" +
                "id ='" + id + '\'' +
                "message ='" + message + '\'' +
                ", sender ='" + sender + '\'' +
                ", timestamp =" + timestamp +
                '}';
    }
}
