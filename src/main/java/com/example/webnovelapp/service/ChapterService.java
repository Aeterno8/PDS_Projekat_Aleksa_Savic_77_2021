package com.example.webnovelapp.service;

import com.example.webnovelapp.model.Chapter;
import com.example.webnovelapp.repository.ChapterRepository;

import java.util.List;
import java.util.Optional;

public class ChapterService {

    private final ChapterRepository chapterRepository;

    public ChapterService() {
        this.chapterRepository = new ChapterRepository();
    }

    public Optional<Chapter> getChapterById(Long id) {
        return chapterRepository.findById(id);
    }

    public List<Chapter> getChaptersByWebnovelId(Long webnovelId) {
        return chapterRepository.findByWebnovelId(webnovelId);
    }

    public void saveChapter(Chapter chapter) {
        chapterRepository.save(chapter);
    }

    public void deleteChapterById(Long id) {
        chapterRepository.deleteById(id);
    }

    public void close() {
        chapterRepository.close();
    }
}