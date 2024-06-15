package com.example.webnovelapp.repository;

import com.example.webnovelapp.model.Comment;
import com.example.webnovelapp.util.DatabaseUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class CommentRepository {

    private EntityManager entityManager = DatabaseUtil.getEntityManager();

    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Comment.class, id));
    }

    public List<Comment> findByWebnovelId(Long webnovelId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);
        Root<Comment> root = cq.from(Comment.class);
        root.fetch("user");
        cq.where(cb.equal(root.get("webnovel").get("id"), webnovelId));
        return entityManager.createQuery(cq).getResultList();
    }

    public List<Comment> findByUserId(Long userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);
        Root<Comment> root = cq.from(Comment.class);
        root.fetch("webnovel");
        cq.where(cb.equal(root.get("user").get("id"), userId));
        return entityManager.createQuery(cq).getResultList();
    }

    public List<Comment> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);
        Root<Comment> root = cq.from(Comment.class);
        cq.orderBy(cb.desc(root.get("timestamp")));
        return entityManager.createQuery(cq).getResultList();
    }

    public void save(Comment comment) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        if (comment.getId() == null) {
            comment.setTimestamp(LocalDateTime.now());
            entityManager.persist(comment);
        } else {
            entityManager.merge(comment);
        }
        transaction.commit();
    }

    public void deleteById(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Comment> cd = cb.createCriteriaDelete(Comment.class);
        Root<Comment> root = cd.from(Comment.class);
        cd.where(cb.equal(root.get("id"), id));
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.createQuery(cd).executeUpdate();
        transaction.commit();
    }

    public void close() {
        DatabaseUtil.closeEntityManager(entityManager);
    }
}