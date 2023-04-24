package com.example.credentials;

import jakarta.ejb.Singleton;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Singleton
public class UserRepository {

    @PersistenceContext(unitName = "buy2buy-cred")
    private EntityManager entityManager;

    public void createUser(User user) {
        entityManager.persist(user);
    }

    public boolean login(String email, String password,String accountType) {
        List<User> result;
        TypedQuery<User> query = entityManager.createQuery("Select c from User C", User.class);
        result = query.getResultList();
        for(User user:result) {
            if(user.getEmail().equals(email)) {
                if(user.getPassword().equals(password)) {
                    if(user.getAccountType().equals(accountType)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

//    public List<User> getCredentialsAccountType(String accountType) {
//        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User U WHERE u.accountType = :accountType ", User.class);
//        query.setParameter("accountType", accountType);
//        List<User> credentials = query.getResultList();
//        return credentials;
//    }

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
