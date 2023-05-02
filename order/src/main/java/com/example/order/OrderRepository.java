package com.example.order;


import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

@Singleton
public class OrderRepository {
    @PersistenceContext(unitName = "buy2buy-order")
    private EntityManager entityManager;
    @Transactional
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
    public List<Order> getUserOrders(String email) {
        // Create a new EntityManager instance
        EntityManager newEntityManager = entityManager.getEntityManagerFactory().createEntityManager();

        TypedQuery<Order> query = newEntityManager.createQuery("SELECT c FROM Order c WHERE c.user.email = :email", Order.class);
        query.setParameter("email",email);
        List<Order> userOrders = query.getResultList();
        return  userOrders;
    }
    public int getLastOrderId(String email) { //get last order id to make a shipping request
        User user = getUserByemail(email);
        List<Order> userOrders = user.getOrders();
        Order lastOrder = userOrders.get(userOrders.size() - 1);
        int lastOrderId = lastOrder.getOrderId();
        return  lastOrderId;
    }

    @Transactional
    public void flushEntityManager() {
        entityManager.flush();
    }

    public String getUserEmail(int orderId) {
        TypedQuery<String> query = entityManager.createQuery("SELECT c.user.email FROM Order c WHERE c.orderId = :orderId", String.class);
        query.setParameter("orderId",orderId);
        String email = query.getSingleResult();
        return email;
    }
}
