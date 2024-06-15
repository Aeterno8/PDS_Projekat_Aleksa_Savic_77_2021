package com.example.webnovelapp.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "webnovels")
public class Webnovel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 5000)
    private String description;

    @ElementCollection
    @CollectionTable(name = "webnovel_authors", joinColumns = @JoinColumn(name = "webnovel_id"))
    @Column(name = "author")
    private List<String> authors;

    @Column(nullable = false)
    private String coverUrl;

    @OneToMany(mappedBy = "webnovel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Chapter> chapters;

    @OneToMany(mappedBy = "webnovel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    public Webnovel() {}

    public Webnovel(String title, List<String> authors, String coverUrl, String description) {
        this.title = title;
        this.authors = authors;
        this.coverUrl = coverUrl;
        this.description = description;
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

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}