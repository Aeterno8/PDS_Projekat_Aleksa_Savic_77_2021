package com.example.webnovelapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "chapters")
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "webnovel_id", nullable = false)
    private Webnovel webnovel;

    public Chapter() {}

    public Chapter(String title, String content, Webnovel webnovel) {
        this.title = title;
        this.content = content;
        this.webnovel = webnovel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Webnovel getWebnovel() {
        return webnovel;
    }

    public void setWebnovel(Webnovel webnovel) {
        this.webnovel = webnovel;
    }
}