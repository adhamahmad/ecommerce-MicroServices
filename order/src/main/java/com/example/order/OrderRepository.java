package com.example.order;


import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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

    public void createCart(Cart cart) {
        entityManager.persist(cart);
    }

    public User getUserByemail(String email) {
        TypedQuery<User> query = entityManager.createQuery("SELECT c FROM User c WHERE c.email = :email ", User.class);
        query.setParameter("email", email);
        User user = query.getSingleResult();
        return user;
    }

    public List<Integer> getUserCart(String email) {
        TypedQuery<Integer> query = entityManager.createQuery("SELECT c.productId FROM Cart c WHERE c.user.email = :email ", Integer.class);
        query.setParameter("email", email);
        List<Integer> productIds = query.getResultList();
        return productIds;
    }
    public void cartReset(String email) {
        Query query = entityManager.createQuery("DELETE FROM Cart c WHERE c.user.email = :email");
        query.setParameter("email", email);
        query.executeUpdate();
    }

    public List<Order> getCompanyOrders(int productId) {
        TypedQuery<Order> query = entityManager.createQuery("SELECT c FROM Order c WHERE CONCAT(',', c.productsId, ',') LIKE :productId", Order.class);
        query.setParameter("productId", "%," + productId + ",%");
        List<Order> companyOrders = query.getResultList();
        return  companyOrders;
    }
}
