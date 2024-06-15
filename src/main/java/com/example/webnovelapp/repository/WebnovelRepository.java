package com.example.webnovelapp.repository;

import com.example.webnovelapp.model.Webnovel;
import com.example.webnovelapp.util.DatabaseUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

public class WebnovelRepository {

    private final EntityManager entityManager;

    public WebnovelRepository() {
        this.entityManager = DatabaseUtil.getEntityManager();
    }

    public Optional<Webnovel> findById(Long id) {
        try {
            return Optional.ofNullable(entityManager.find(Webnovel.class, id));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Webnovel> findByTitle(String title) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Webnovel> cq = cb.createQuery(Webnovel.class);
        Root<Webnovel> root = cq.from(Webnovel.class);
        cq.select(root).where(cb.equal(root.get("title"), title));
        return entityManager.createQuery(cq).getResultList().stream().findFirst();
    }

    public List<Webnovel> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Webnovel> cq = cb.createQuery(Webnovel.class);
        Root<Webnovel> root = cq.from(Webnovel.class);
        cq.select(root);
        try {
            return entityManager.createQuery(cq).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Webnovel> findFirstFive() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Webnovel> cq = cb.createQuery(Webnovel.class);
        Root<Webnovel> root = cq.from(Webnovel.class);
        cq.select(root).orderBy(cb.asc(root.get("id")));
        return entityManager.createQuery(cq).setMaxResults(5).getResultList();
    }

    public List<Webnovel> findNextFive(Long lastWebnovelId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Webnovel> cq = cb.createQuery(Webnovel.class);
        Root<Webnovel> root = cq.from(Webnovel.class);
        cq.select(root).where(cb.gt(root.get("id"), lastWebnovelId)).orderBy(cb.asc(root.get("id")));
        return entityManager.createQuery(cq).setMaxResults(5).getResultList();
    }

    public void save(Webnovel webnovel) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (webnovel.getId() == null) {
                entityManager.persist(webnovel);
            } else {
                entityManager.merge(webnovel);
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
        CriteriaDelete<Webnovel> cd = cb.createCriteriaDelete(Webnovel.class);
        Root<Webnovel> root = cd.from(Webnovel.class);
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