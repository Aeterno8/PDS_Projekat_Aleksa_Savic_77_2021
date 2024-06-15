package com.example.webnovelapp.beans;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CommentBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String content;
    private Long userId;
    private Long webnovelId;
    private LocalDateTime timestamp;

    public CommentBean() {
    }

    public CommentBean(Long id, String content, Long userId, Long webnovelId, LocalDateTime timestamp) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.webnovelId = webnovelId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWebnovelId() {
        return webnovelId;
    }

    public void setWebnovelId(Long webnovelId) {
        this.webnovelId = webnovelId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}