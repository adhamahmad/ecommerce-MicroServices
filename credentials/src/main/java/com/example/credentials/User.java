package com.example.credentials;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "credentials")
public class User  {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(name = "`account-type`")
    private String accountType;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
