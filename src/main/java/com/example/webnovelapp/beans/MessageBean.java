package com.example.webnovelapp.beans;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MessageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String content;
    private Long senderId;
    private Long recipientId;
    private LocalDateTime timestamp;

    public MessageBean() {
    }

    public MessageBean(Long id, String content, Long senderId, Long recipientId, LocalDateTime timestamp) {
        this.id = id;
        this.content = content;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}