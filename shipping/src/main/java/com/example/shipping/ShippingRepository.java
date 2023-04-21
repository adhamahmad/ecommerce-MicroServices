package com.example.shipping;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Singleton
public class ShippingRepository {
    @PersistenceContext(unitName = "buy2buy-shipping")
    private EntityManager entityManager;

    public void createShipping(ShippingCompany shippingCompany) {
        entityManager.persist(shippingCompany);
    }

}
