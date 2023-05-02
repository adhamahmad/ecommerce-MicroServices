package com.example.shipping;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "`shipping-company`")
public class ShippingCompany {
    @Id
    @Column(name = "`shipping-name`")
    private String shippingName;
    @Column(name = "email")
    private String email;

    @Column(name = "`supported-regions`")
    private String supportedRegions;

    @OneToMany(mappedBy = "shippingCompany")
    private List<ShippingRequest> shippingRequests;

    public List<ShippingRequest> getShippingRequests() {
        return shippingRequests;
    }

    public void setShippingRequests(List<ShippingRequest> shippingRequests) {
        this.shippingRequests = shippingRequests;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSupportedRegions() {
        return supportedRegions;
    }

    public void setSupportedRegions(String supportedRegions) {
        this.supportedRegions = supportedRegions;
    }

}
