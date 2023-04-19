package com.example.credentials;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class UserRepository {

    @PersistenceContext(unitName = "buy2buy-cred")
    private EntityManager entityManager;

    public void createUser(User user) {
        entityManager.persist(user);
    }

    public User readUser(int id) {
        return entityManager.find(User.class, id);
    }

    public void updateUser(User user) {
        entityManager.merge(user);
    }

    public void deleteUser(int id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }
}
