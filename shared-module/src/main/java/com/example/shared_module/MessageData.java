package com.example.shared_module;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageData {

    private String message;
    private String sender;
    private LocalDateTime timestamp;

    @Override
    public String toString() {
        return "com.example.shared_module.MessageData{" +
                "message='" + message + '\'' +
                ", sender='" + sender + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
