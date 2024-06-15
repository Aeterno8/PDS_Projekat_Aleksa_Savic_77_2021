package com.example.webnovelapp.service;

import com.example.webnovelapp.model.Webnovel;
import com.example.webnovelapp.repository.WebnovelRepository;
import com.example.webnovelapp.model.Chapter;
import com.example.webnovelapp.repository.ChapterRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Random;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.Enumeration;

public class WebnovelService {

    private final WebnovelRepository webnovelRepository;
    private final ChapterRepository chapterRepository;

    public WebnovelService() {
        this.webnovelRepository = new WebnovelRepository();
        this.chapterRepository = new ChapterRepository();
    }

    @Transactional
    public void addWebnovelsFromZipFile(String zipFilePath) throws IOException {

        File tempDir = Files.createTempDirectory("webnovels").toFile();
        unzip(zipFilePath, tempDir.getAbsolutePath());

        File[] directories = tempDir.listFiles(File::isDirectory);
        if (directories != null) {
            for (File dir : directories) {
                File metaFile = new File(dir, "meta.json");
                if (metaFile.exists()) {
                    processWebnovelDirectory(dir);
                }
            }
        }

        FileUtils.deleteDirectory(tempDir);
    }

    private void unzip(String zipFilePath, String destDir) throws IOException {
        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                File entryDestination = new File(destDir, entry.getName());
                if (entry.isDirectory()) {
                    entryDestination.mkdirs();
                } else {
                    entryDestination.getParentFile().mkdirs();
                    Files.copy(zipFile.getInputStream(entry), entryDestination.toPath());
                }
            }
        }
    }

    private void processWebnovelDirectory(File webnovelDir) throws IOException {
        File metaFile = new File(webnovelDir, "meta.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode metaJson = mapper.readTree(metaFile);

        String title = metaJson.path("novel").path("title").asText();
        String[] authorsArray = mapper.convertValue(metaJson.path("novel").path("authors"), String[].class);
        List<String> authors = Arrays.asList(authorsArray);
        String coverUrl = metaJson.path("novel").path("cover_url").asText();
        String description = metaJson.path("novel").path("url").asText();

        Optional<Webnovel> existingWebnovel = webnovelRepository.findByTitle(title);

        if (!existingWebnovel.isPresent()) {
            Webnovel newWebnovel = new Webnovel(title, authors, coverUrl, description);
            webnovelRepository.save(newWebnovel);

            File jsonDir = new File(webnovelDir, "json");
            if (jsonDir.exists() && jsonDir.isDirectory()) {
                File[] chapterFiles = jsonDir.listFiles((dir, name) -> name.endsWith(".json"));
                if (chapterFiles != null) {
                    for (File chapterFile : chapterFiles) {
                        JsonNode chapterJson = mapper.readTree(chapterFile);
                        String chapterTitle = chapterJson.path("title").asText();
                        String chapterBody = chapterJson.path("body").asText();

                        Chapter chapter = new Chapter(chapterTitle, chapterBody, newWebnovel);
                        chapterRepository.save(chapter);
                    }
                }
            }

            System.out.println("Webnovel added: " + title);
        } else {
            System.out.println("Webnovel already exists: " + title);
        }
    }


    @Transactional
    public Optional<Chapter> getChapterById(Long id) {
        return chapterRepository.findById(id);
    }

    public Optional<Webnovel> getWebnovelById(Long id) {
        return webnovelRepository.findById(id);
    }

    public List<Webnovel> getAllWebnovels() {
        return webnovelRepository.findAll();
    }

    @Transactional
    public void saveWebnovel(Webnovel webnovel) {
        webnovelRepository.save(webnovel);
    }

    @Transactional
    public void deleteWebnovelById(Long id) {
        try {
            webnovelRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public Optional<Long> getNextChapterId(Long currentChapterId) {
        return chapterRepository.findNextChapterId(currentChapterId);
    }

    @Transactional
    public Optional<Long> getPreviousChapterId(Long currentChapterId) {
        return chapterRepository.findPreviousChapterId(currentChapterId);
    }

    @Transactional
    public Long getWebnovelIdByChapterId(Long chapterId) {
        return chapterRepository.findWebnovelIdByChapterId(chapterId)
                .orElse(null);
    }

    public List<Webnovel> getFirstFiveWebnovels() {
        return webnovelRepository.findFirstFive();
    }

    public List<Webnovel> getNextFiveWebnovels(Long lastWebnovelId) {
        return webnovelRepository.findNextFive(lastWebnovelId);
    }

    public void close() {
        webnovelRepository.close();
        chapterRepository.close();
    }
}