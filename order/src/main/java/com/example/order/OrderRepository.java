package com.example.order;


import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
}
