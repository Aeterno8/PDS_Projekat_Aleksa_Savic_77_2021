package com.example.webnovelapp.repository;

import com.example.webnovelapp.model.ChatRoom;
import com.example.webnovelapp.util.DatabaseUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

public class ChatRoomRepository {

    private final EntityManager entityManager;

    public ChatRoomRepository() {
        this.entityManager = DatabaseUtil.getEntityManager();
    }

    public Optional<ChatRoom> findById(Long id) {
        try {
            return Optional.ofNullable(entityManager.find(ChatRoom.class, id));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<ChatRoom> findByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ChatRoom> cq = cb.createQuery(ChatRoom.class);
        Root<ChatRoom> root = cq.from(ChatRoom.class);
        cq.select(root).where(cb.equal(root.get("name"), name));
        return entityManager.createQuery(cq).getResultList().stream().findFirst();
    }

    public List<ChatRoom> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ChatRoom> cq = cb.createQuery(ChatRoom.class);
        Root<ChatRoom> root = cq.from(ChatRoom.class);
        cq.select(root);
        try {
            return entityManager.createQuery(cq).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public void save(ChatRoom chatRoom) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (chatRoom.getId() == null) {
                entityManager.persist(chatRoom);
            } else {
                entityManager.merge(chatRoom);
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
        CriteriaDelete<ChatRoom> cd = cb.createCriteriaDelete(ChatRoom.class);
        Root<ChatRoom> root = cd.from(ChatRoom.class);
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