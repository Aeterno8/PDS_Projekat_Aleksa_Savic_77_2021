package com.example.webnovelapp.beans;

import java.io.Serializable;

public class ChapterBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String content;
    private Long webnovelId;

    public ChapterBean() {
    }

    public ChapterBean(Long id, String title, String content, Long webnovelId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.webnovelId = webnovelId;
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

    public Long getWebnovelId() {
        return webnovelId;
    }

    public void setWebnovelId(Long webnovelId) {
        this.webnovelId = webnovelId;
    }
}