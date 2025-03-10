package com.kodilla.ecommercee.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class OrderDto {

    private int id;
    private LocalDate orderDate;
    private int userId;
    private double totalPrice;

}
