package com.example.product;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Singleton
public class SellingRepository {
    @PersistenceContext(unitName = "buy2buy-product")
    private EntityManager entityManager;

    public void createSelling(SellingCompany sellingCompany) {
        entityManager.persist(sellingCompany);
    }
    public List<SellingCompany> getSellingCompanies() {
        TypedQuery<SellingCompany> query = entityManager.createQuery("SELECT c FROM SellingCompany c ", SellingCompany.class);
        List<SellingCompany> companies = query.getResultList();
        return companies;
    }
}
