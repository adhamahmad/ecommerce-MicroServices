package com.example.order;

import jakarta.persistence.*;

@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`order-id`")
    private int orderId;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productsId;
    }

    public void setProductId(String productId) {
        this.productsId = productId;
    }

    @Column(name = "`products-id`")
    private String productsId;

    @ManyToOne
    @JoinColumn(name = "`user-email`", referencedColumnName = "email")
    private User user;

    @Column(name = "`shipping-name`")
    private String shippingName;



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Column(name = "`order-amount`")
    private Integer orderAmount;

    @Column(name = "`order-status`")
    private String orderStatus;
}
