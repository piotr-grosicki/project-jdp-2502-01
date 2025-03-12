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
@Entity(name = "PRODUCTS")
public class Products {

    private int id;
    private List<Cart> carts = new ArrayList<>();

    @Id
    @GeneratedValue
    @Column(name = "PRODUCT_ID")
    public int getId() {
        return id;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "JOIN_CART_PRODUCT",
            joinColumns = {@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "CART_ID", referencedColumnName = "CART_ID")}
    )
    public List<Cart> getCarts() {
        return carts;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
