package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CART")
public class Cart {

    private int id;
    private Users users;
    private List<Product> products = new ArrayList<>();

    @Id
    @GeneratedValue
    @Column(name = "CART_ID")
    public int getId() {
        return id;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    public Users getUsers() {
        return users;
    }

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "carts")
    public List<Product> getProducts() {
        return products;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
