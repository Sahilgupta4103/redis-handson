package com.example.redisHandson.Model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "customers")
public class CustomerEntity implements Serializable {

    @Id
    private Long id;
    private String name;
    private String email;
    private String city;

    public CustomerEntity(){}

    public CustomerEntity(Long id, String name, String email, String city){
        this.id = id;
        this.name = name;
        this.email = email;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
