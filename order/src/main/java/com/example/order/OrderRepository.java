package com.example.order;


import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Singleton
public class OrderRepository {
    @PersistenceContext(unitName = "buy2buy-order")
    private EntityManager entityManager;

    public void createOrder(Order order) {
        entityManager.persist(order);
    }
    public void createUser(User user) {
        entityManager.persist(user);
    }

    public List<User> getUsers() {
        TypedQuery<User> query = entityManager.createQuery("SELECT c FROM User c ", User.class);
        List<User> users = query.getResultList();
        return users;
    }
}
