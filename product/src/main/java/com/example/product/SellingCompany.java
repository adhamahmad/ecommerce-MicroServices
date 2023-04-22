package com.example.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "`selling-company`")
public class SellingCompany {
    @Id
    @Column(name = "`selling-name`")
    private String sellingName;
    @Column(name = "email")
    private String email;

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
