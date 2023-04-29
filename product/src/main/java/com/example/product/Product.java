package com.example.product;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "`product-id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int productId;

    @Column(name = "`product-name`")
    private String productName;

    @Column(name = "price")
    private Double price;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public SellingCompany getSellingCompany() {
        return sellingCompany;
    }

    public void setSellingCompany(SellingCompany sellingCompany) {
        this.sellingCompany = sellingCompany;
    }

    @ManyToOne
    @JoinColumn(name = "`selling-name`")
    private SellingCompany sellingCompany;
}
