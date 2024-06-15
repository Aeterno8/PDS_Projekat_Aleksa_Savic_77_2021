package com.example.webnovelapp.beans;

import java.io.Serializable;
import java.util.List;

public class WebnovelBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String description;
    private List<ChapterBean> chapters;
    private List<CommentBean> comments;

    public WebnovelBean() {
    }

    public WebnovelBean(Long id, String title, String description, List<ChapterBean> chapters, List<CommentBean> comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.chapters = chapters;
        this.comments = comments;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ChapterBean> getChapters() {
        return chapters;
    }

    public void setChapters(List<ChapterBean> chapters) {
        this.chapters = chapters;
    }

    public List<CommentBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentBean> comments) {
        this.comments = comments;
    }
}