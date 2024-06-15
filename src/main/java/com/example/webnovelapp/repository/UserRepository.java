package com.example.webnovelapp.repository;

import com.example.webnovelapp.model.User;
import com.example.webnovelapp.util.DatabaseUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

public class UserRepository {

    private EntityManager entityManager = DatabaseUtil.getEntityManager();

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    public Optional<User> findByUsername(String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.where(cb.equal(root.get("username"), username));
        return entityManager.createQuery(cq).getResultList().stream().findFirst();
    }

    public Optional<User> findByEmail(String email) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.where(cb.equal(root.get("email"), email));
        return entityManager.createQuery(cq).getResultList().stream().findFirst();
    }

    public List<User> findByEnabled(boolean enabled) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.where(cb.equal(root.get("enabled"), enabled));
        return entityManager.createQuery(cq).getResultList();
    }

    public List<User> findByNameContainingIgnoreCase(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.where(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        return entityManager.createQuery(cq).getResultList();
    }

    public long countByEmail(String email) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<User> root = cq.from(User.class);
        cq.select(cb.count(root)).where(cb.equal(root.get("email"), email));
        return entityManager.createQuery(cq).getSingleResult();
    }

    public void deleteByUsername(String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<User> cd = cb.createCriteriaDelete(User.class);
        Root<User> root = cd.from(User.class);
        cd.where(cb.equal(root.get("username"), username));
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.createQuery(cd).executeUpdate();
        transaction.commit();
    }

    public void save(User user) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        if (user.getId() == null) {
            entityManager.persist(user);
        } else {
            entityManager.merge(user);
        }
        transaction.commit();
    }

    public void addUser(User user) {
        save(user);
    }

    public Optional<User> findBySessionId(String sessionId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.where(cb.equal(root.get("sessionId"), sessionId));
        return entityManager.createQuery(cq).getResultList().stream().findFirst();
    }

    public void close() {
        DatabaseUtil.closeEntityManager(entityManager);
    }
}