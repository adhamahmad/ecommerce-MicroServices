package com.example.product;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Singleton
public class SellingRepository {
    @PersistenceContext(unitName = "buy2buy-product")
    private EntityManager entityManager;

    public void createSelling(SellingCompany sellingCompany) {
        entityManager.persist(sellingCompany);
    }
}
