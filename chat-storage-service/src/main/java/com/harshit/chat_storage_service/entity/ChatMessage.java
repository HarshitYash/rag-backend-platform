package com.harshit.chat_storage_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "chat_messages")
public class ChatMessage implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sessionId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    public ChatMessage() {
    }

    public ChatMessage(String sessionId, String message) {
        this.sessionId = sessionId;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}