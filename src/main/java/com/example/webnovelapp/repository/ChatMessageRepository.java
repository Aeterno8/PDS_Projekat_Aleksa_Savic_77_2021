package com.example.webnovelapp.repository;

import com.example.webnovelapp.model.ChatMessage;
import com.example.webnovelapp.util.DatabaseUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

public class ChatMessageRepository {

    private final EntityManager entityManager;

    public ChatMessageRepository() {
        this.entityManager = DatabaseUtil.getEntityManager();
    }

    public Optional<ChatMessage> findById(Long id) {
        try {
            return Optional.ofNullable(entityManager.find(ChatMessage.class, id));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<ChatMessage> findByChatRoomId(Long chatRoomId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ChatMessage> cq = cb.createQuery(ChatMessage.class);
        Root<ChatMessage> root = cq.from(ChatMessage.class);
        cq.select(root).where(cb.equal(root.get("chatRoom").get("id"), chatRoomId));
        return entityManager.createQuery(cq).getResultList();
    }

    public List<ChatMessage> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ChatMessage> cq = cb.createQuery(ChatMessage.class);
        Root<ChatMessage> root = cq.from(ChatMessage.class);
        cq.select(root);
        try {
            return entityManager.createQuery(cq).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public void save(ChatMessage chatMessage) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (chatMessage.getId() == null) {
                entityManager.persist(chatMessage);
            } else {
                entityManager.merge(chatMessage);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteById(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<ChatMessage> cd = cb.createCriteriaDelete(ChatMessage.class);
        Root<ChatMessage> root = cd.from(ChatMessage.class);
        cd.where(cb.equal(root.get("id"), id));
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.createQuery(cd).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            entityManager.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}