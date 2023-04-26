package com.example.product;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "`selling-company`")
public class SellingCompany {
    @Id
    @Column(name = "`selling-name`")
    private String sellingName;
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "sellingCompany")
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getSellingName() {
        return sellingName;
    }

    public void setSellingName(String sellingName) {
        this.sellingName = sellingName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
