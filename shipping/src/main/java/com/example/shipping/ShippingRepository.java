package com.example.shipping;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Singleton
public class ShippingRepository {
    @PersistenceContext(unitName = "buy2buy-shipping")
    private EntityManager entityManager;

    public void createShipping(ShippingCompany shippingCompany) {
        entityManager.persist(shippingCompany);
    }

    public List<ShippingCompany> getShippingCompanies() {
        TypedQuery<ShippingCompany> query = entityManager.createQuery("SELECT c FROM ShippingCompany c ", ShippingCompany.class);
        List<ShippingCompany> companies = query.getResultList();
        return companies;
    }

}
