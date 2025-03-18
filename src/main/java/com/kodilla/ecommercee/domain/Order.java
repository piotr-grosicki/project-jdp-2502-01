package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "orders")
@Table(name = "ORDERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private int id;

    @Column(name = "DATE_OF_ORDER", nullable = false)
    private LocalDate orderDate;

    @Column(name = "USER_ID", nullable = false)
    private int userId;

    @Column(name = "TOTAL_PRICE", nullable = false)
    private BigDecimal totalPrice;


//    Relacja zakomentowana w oczekiwaniu na klasę User
//
//    @ManyToOne
//    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
//    private User user;

    @ManyToMany
    @JoinTable(
            name = "ORDER_PRODUCT",
            joinColumns = {@JoinColumn(name = "ORDER_ID", referencedColumnName = "ORDER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")}
    )
    private List<Product> products;

}

