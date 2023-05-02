package com.example.shipping;

import jakarta.persistence.*;
@Entity
@Table(name = "shippingrequests")
public class ShippingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requestId")
    private int requestId;

    @Column(name = "orderId")
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "`shipping-name`")
    private ShippingCompany shippingCompany;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public ShippingCompany getShippingCompany() {
        return shippingCompany;
    }

    public void setShippingCompany(ShippingCompany shippingCompany) {
        this.shippingCompany = shippingCompany;
    }
}