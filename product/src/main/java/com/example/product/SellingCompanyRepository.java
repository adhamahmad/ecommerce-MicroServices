package com.example.product;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class SellingCompanyRepository {
    @PersistenceContext(unitName = "buy2buy-product")
    private EntityManager entityManager;

    public void createSellingCompany(SellingCompany selling) {
        entityManager.persist(selling);
    }

    public SellingCompany readSellingCompany(String name) {
        return entityManager.find(SellingCompany.class, name);
    }

    public void updateSellingCompany(SellingCompany selling) {
        entityManager.merge(selling);
    }

    public void deleteSellingCompany(String name) {
        SellingCompany selling = entityManager.find(SellingCompany.class, name);
        if (selling != null) {
            entityManager.remove(selling);
        }
    }
}
