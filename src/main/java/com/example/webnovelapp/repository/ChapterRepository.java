package com.example.webnovelapp.repository;

import com.example.webnovelapp.model.Chapter;
import com.example.webnovelapp.util.DatabaseUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

public class ChapterRepository {

    private EntityManager entityManager = DatabaseUtil.getEntityManager();

    public Optional<Chapter> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Chapter.class, id));
    }

    public List<Chapter> findByWebnovelId(Long webnovelId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Chapter> cq = cb.createQuery(Chapter.class);
        Root<Chapter> root = cq.from(Chapter.class);
        root.fetch("webnovel"); // Eager fetch the associated Webnovel entity
        cq.where(cb.equal(root.get("webnovel").get("id"), webnovelId));
        return entityManager.createQuery(cq).getResultList();
    }

    public void save(Chapter chapter) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        if (chapter.getId() == null) {
            entityManager.persist(chapter);
        } else {
            entityManager.merge(chapter);
        }
        transaction.commit();
    }

    public void deleteById(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Chapter> cd = cb.createCriteriaDelete(Chapter.class);
        Root<Chapter> root = cd.from(Chapter.class);
        cd.where(cb.equal(root.get("id"), id));
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.createQuery(cd).executeUpdate();
        transaction.commit();
    }

    public Optional<Long> findNextChapterId(Long currentChapterId) {
        try {
            Chapter currentChapter = entityManager.find(Chapter.class, currentChapterId);
            if (currentChapter == null) {
                return Optional.empty();
            }
            Long nextChapterId = entityManager.createQuery("SELECT MIN(c.id) FROM Chapter c WHERE c.webnovel.id = :webnovelId AND c.id > :currentChapterId", Long.class)
                    .setParameter("webnovelId", currentChapter.getWebnovel().getId())
                    .setParameter("currentChapterId", currentChapterId)
                    .getSingleResult();
            return Optional.ofNullable(nextChapterId);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Long> findPreviousChapterId(Long currentChapterId) {
        try {
            Chapter currentChapter = entityManager.find(Chapter.class, currentChapterId);
            if (currentChapter == null) {
                return Optional.empty();
            }
            Long previousChapterId = entityManager.createQuery("SELECT MAX(c.id) FROM Chapter c WHERE c.webnovel.id = :webnovelId AND c.id < :currentChapterId", Long.class)
                    .setParameter("webnovelId", currentChapter.getWebnovel().getId())
                    .setParameter("currentChapterId", currentChapterId)
                    .getSingleResult();
            return Optional.ofNullable(previousChapterId);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Long> findWebnovelIdByChapterId(Long chapterId) {
        try {
            Chapter chapter = entityManager.find(Chapter.class, chapterId);
            if (chapter != null) {
                return Optional.of(chapter.getWebnovel().getId());
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public void close() {
        DatabaseUtil.closeEntityManager(entityManager);
    }
}