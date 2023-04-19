package com.example.product;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "product-id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "product-name")
    private String name;

    @Column(name = "product-image")
    private String image;

    @Column(name = "price")
    private int price;

    @Column(name = "quantity-in-stock")
    private int quantity;

    @Column(name = "selling-name")
    private String sellingCompany;

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSellingCompany() {
        return sellingCompany;
    }

    public void setSellingCompany(String sellingCompany) {
        this.sellingCompany = sellingCompany;
    }
}
