package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "ORDERS")
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "ORDER ID")
    private int id;

    @Column(name = "DATE OF ORDER", nullable = false)
    private LocalDate orderDate;

    @Column(name = "USER ID", nullable = false)
    private int userId;

    @Column(name = "TOTAL PRICE", nullable = false)
    private double totalPrice;

    /*
    Relacja zakomentowana w oczekiwaniu na klasę User

    @ManyToOne
    @JoinColumn(name = "USER ID", referencedColumnName = "USER ID", insertable = false, updatable = false)
    private User user;
     */
}

