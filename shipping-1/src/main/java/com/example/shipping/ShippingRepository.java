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
    public void createRequest(ShippingRequest shippingRequest) {
        entityManager.persist(shippingRequest);
    }

    public List<ShippingCompany> getShippingCompanies() {
        TypedQuery<ShippingCompany> query = entityManager.createQuery("SELECT c FROM ShippingCompany c ", ShippingCompany.class);
        List<ShippingCompany> companies = query.getResultList();
        return companies;
    }

    public List<ShippingCompany> getShippingCompanies(String location) {
        TypedQuery<ShippingCompany> query = entityManager.createQuery("SELECT c FROM ShippingCompany c WHERE CONCAT(',', c.supportedRegions, ',') LIKE :location", ShippingCompany.class);
        query.setParameter("location", "%," + location + ",%");
        List<ShippingCompany> shippingCompanies = query.getResultList();
        return  shippingCompanies;
    }

    public ShippingCompany getShippingCompany(String shippingName) {
        TypedQuery<ShippingCompany> query = entityManager.createQuery("SELECT s FROM ShippingCompany s WHERE s.shippingName = :shippingName", ShippingCompany.class);
        query.setParameter("shippingName", shippingName);
        ShippingCompany sellingCompany = query.getSingleResult(); //shipping name is unique
        return  sellingCompany;
    }

    public String getShippingName(String email) {
        TypedQuery<String> query = entityManager.createQuery("SELECT c.shippingName FROM ShippingCompany c WHERE c.email = :email", String.class);
        query.setParameter("email",email);
        String shippingName = query.getSingleResult();
        return shippingName;
    }

    public void deleteRequest(int requestId) {
        TypedQuery<ShippingRequest> query = entityManager.createQuery(
                "SELECT sr FROM ShippingRequest sr WHERE sr.requestId = :id", ShippingRequest.class);
        query.setParameter("id", requestId);
        ShippingRequest request = query.getSingleResult();
        entityManager.remove(request);
    }

    public List<ShippingRequest> getCompanyShippingRequests(String shippingName) {
        // Create a new EntityManager instance
        EntityManager newEntityManager = entityManager.getEntityManagerFactory().createEntityManager();

        TypedQuery<ShippingRequest> query = newEntityManager.createQuery("SELECT sr FROM ShippingRequest sr WHERE sr.shippingCompany.shippingName = :shippingName", ShippingRequest.class);
        query.setParameter("shippingName",shippingName);
        List<ShippingRequest> requests = query.getResultList();
        return  requests;
    }
}
