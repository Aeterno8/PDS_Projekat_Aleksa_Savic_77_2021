package com.example.webnovelapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "webnovel_id", nullable = false)
    private Webnovel webnovel;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public Comment() {}

    public Comment(String content, User user, Webnovel webnovel, LocalDateTime timestamp) {
        this.content = content;
        this.user = user;
        this.webnovel = webnovel;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Webnovel getWebnovel() {
        return webnovel;
    }

    public void setWebnovel(Webnovel webnovel) {
        this.webnovel = webnovel;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}