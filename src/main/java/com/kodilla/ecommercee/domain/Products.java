package com.kodilla.ecommercee.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "PRODUCTS")
@Setter
public class Products {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private int id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "JOIN_CART_PRODUCT",
            joinColumns = {@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "CART_ID", referencedColumnName = "CART_ID")}
    )
    private List<Cart> carts = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    @JsonIgnore
    private Group group;
}




