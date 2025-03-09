package com.kodilla.ecommercee.domain;

import java.time.LocalDate;

public class OrderDto {

    private long id;
    private LocalDate orderDate;
    private int userId;
    private double totalPrice;

    public OrderDto(long id, int userId, double totalPrice) {
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
    }

    public long getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
