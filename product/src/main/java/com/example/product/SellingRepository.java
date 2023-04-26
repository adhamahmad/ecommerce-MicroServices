package com.example.product;

import jakarta.ejb.Singleton;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Stateless
public class SellingRepository {
    @PersistenceContext(unitName = "buy2buy-product")
    private EntityManager entityManager;

    public void createSelling(SellingCompany sellingCompany) {
        entityManager.persist(sellingCompany);
    }
    public void createProduct(Product product) {
        entityManager.persist(product);
    }
    public List<SellingCompany> getSellingCompanies() {
        TypedQuery<SellingCompany> query = entityManager.createQuery("SELECT c FROM SellingCompany c ", SellingCompany.class);
        List<SellingCompany> companies = query.getResultList();
        return companies;
    }
    public List<Product> getCompanyProducts(String sellingName) {
        TypedQuery<Product> query = entityManager.createQuery("SELECT c FROM Product c WHERE c.sellingCompany.sellingName = :sellingName AND c.quantity >0", Product.class);
        query.setParameter("sellingName", sellingName);
        List<Product> products = query.getResultList();
        return products;
    }

    public String  getCompanyName(String email) {
        TypedQuery<SellingCompany> query = entityManager.createQuery("SELECT s FROM SellingCompany s WHERE s.email = :email", SellingCompany.class);
        query.setParameter("email", email);
        SellingCompany sellingCompany = query.getSingleResult();
        String sellingName = sellingCompany.getSellingName();
        return sellingName;
    }

    public SellingCompany getCompany(String sellingName){
        TypedQuery<SellingCompany> query = entityManager.createQuery("SELECT s FROM SellingCompany s WHERE s.sellingName = :sellingName", SellingCompany.class);
        query.setParameter("sellingName", sellingName);
        SellingCompany sellingCompany = query.getSingleResult(); //selling name is unique
        return  sellingCompany;
    }
}
