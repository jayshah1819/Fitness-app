package com.fitness.mcpservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "messages")
public class Message {
    @Id
    private String id;
    private String senderId;
    private String receiverId;
    private String content;
    private String type;
    private LocalDateTime timestamp;
    private MessageStatus status;
    
    public enum MessageStatus {
        SENT,
        DELIVERED,
        READ
    }
} 